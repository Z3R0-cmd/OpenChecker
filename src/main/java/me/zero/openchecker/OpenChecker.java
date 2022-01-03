package me.zero.openchecker;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.util.stream.Collectors.*;

public class OpenChecker implements Opcodes {
    public static void main(String[] args) {
        new CheckerGUI().setVisible(true);
    }


    public static void checkJar(String filePath, JTextArea logsArea) throws IOException {
        File jar = new File(filePath);

        if(!jar.exists()) {
            JOptionPane.showMessageDialog(null, "That jarfile doesnt exist!");
            return;
        }

        if(jar.isDirectory()) {
            JOptionPane.showMessageDialog(null, "You cant scan folders, only sentinel can do that...");
            return;
        }

        if(!jar.getName().endsWith(".jar")) {
            JOptionPane.showMessageDialog(null, "Does that look like a .jar to you???");
            return;
        }

        Map<String, ClassNode> classes = JarUtils.loadClasses(jar);



        for(ClassNode cn: classes.values().stream().sorted(new ClassSorter()).collect(toList())) {


            List<String> flags = new ArrayList<>();

            for(MethodNode mn: cn.methods) {
                for(AbstractInsnNode ain: mn.instructions.toArray()) {
                    if(ain instanceof MethodInsnNode) {
                        MethodInsnNode method = (MethodInsnNode) ain;

                        if(method.owner.equalsIgnoreCase("com/sun/jna/Native")
                                && method.name.equalsIgnoreCase("extractFromResourcePath")) {
                            flags.add("Extracts native code!");
                        }

                        if (method.name.equalsIgnoreCase("loadClass")) {
                            flags.add("Loads a class!");
                        }
                    }
                    if(ain instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode) ain;

                        if (ldc.cst.toString().toLowerCase().contains("checkip.amazonaws.com")
                                || ldc.cst.toString().toLowerCase().contains("whatismyipaddress.com")) {
                            flags.add("Has an ip getter url string!");
                        }
                    }

                    if(ain instanceof TypeInsnNode) {
                        TypeInsnNode type = (TypeInsnNode) ain;
                        if (type.getOpcode() == NEW && type.desc.equalsIgnoreCase("java/awt/Robot")) {
                            flags.add("Creates a new Robot! (commonly used for screenshots)");
                        }
                    }
                }
            }


            if(!flags.isEmpty()) {
                logsArea.setText(logsArea.getText() + cn.name + "\n");

                for(String s: flags) {
                    logsArea.setText(logsArea.getText() + " - " + s + "\n");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }

    public static class ClassSorter implements Comparator<ClassNode> {

        @Override
        public int compare(ClassNode o1, ClassNode o2) {
            return o1.name.compareTo(o2.name);
        }
    }
}

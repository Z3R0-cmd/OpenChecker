/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package me.zero.openchecker;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author zero
 */
public class CheckerGUI extends javax.swing.JFrame {

    /**
     * Creates new form CheckerGUI
     */
    public CheckerGUI() {
        initComponents();
    }


    @SuppressWarnings({"unchecked", "depracated"})
    private void initComponents() {

        setTitle("OpenChecker v1 by Z3R0");


//        JOptionPane.showMessageDialog(this, "If you enjoy this checker, please try out sentinel at https://discord.gg/DY2nzUXdP3\nbtw this checker is supposed to be bad, sentinel is way better and has obf/deobf features and more...");
//
//        try {
//            Desktop.getDesktop().browse(new URL("https://discord.gg/DY2nzUXdP3").toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


        jarPath = new javax.swing.JTextField();
        checkButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logsArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jarPath.setText("Put a file path here. there is no drag/drop if u want that get sentinel");

        checkButton.setText("Check");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OpenChecker.checkJar(jarPath.getText(), logsArea);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "ERROR!\n" + ioException.getMessage());
                }
            }
        });

        logsArea.setColumns(20);
        logsArea.setRows(5);
        logsArea.setEditable(false);
        jScrollPane1.setViewportView(logsArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jarPath, GroupLayout.PREFERRED_SIZE, 645, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkButton)
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jarPath, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(checkButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logsArea;
    private javax.swing.JTextField jarPath;
    // End of variables declaration//GEN-END:variables
}

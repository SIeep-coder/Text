package com.Test;

import javax.swing.*;

public class EditText {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                EditFrame editFrame = new EditFrame();

                JFrame jFrame = new JFrame();
                JPanel jPanel = new javax.swing.JPanel(){
                    protected void paintComponent(java.awt.Graphics g){
                        super.paintComponent(g);
                        g.drawImage(new ImageIcon("experiment_bac.jpg").getImage(),0,0,400,250,null);
                    }
                };
                jFrame.add(jPanel);
                jFrame.setVisible(true);
                jFrame.setSize(400, 300);
                jFrame.setLocationRelativeTo(null);

                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jFrame.dispose();
                editFrame.getjFrame().setVisible(true);
            }
        }.start();
    }
}
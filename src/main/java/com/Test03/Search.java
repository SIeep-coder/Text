package com.Test03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Search extends JDialog{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel l1=new JLabel("查找内容");
    JTextField t1=new JTextField(10);
    JButton b1=new JButton("查找下一个");
    JButton b2=new JButton("取消");
    Font f1=new Font("隶书",Font.PLAIN,15);
    public Search(){
        setTitle("查找");
        setSize(300,200);
        setLayout(new FlowLayout());
        b1.setFont(f1);
        b2.setFont(f1);
        l1.setFont(f1);
        add(l1);
        add(t1);
        add(b1);
        add(b2);
        b2.addActionListener(new MyActionListener2());
    }
    class MyActionListener2 implements ActionListener{
        public void actionPerformed(ActionEvent e1) {
            setVisible(false);
        }

    }
}
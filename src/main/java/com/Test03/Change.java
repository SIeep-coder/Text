package com.Test03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Change extends JDialog{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel l1=new JLabel("查找内容");
    JLabel l2=new JLabel("替换为");
    JTextField t1=new JTextField(10);
    JTextField t2=new JTextField(10);
    JButton b1=new JButton("替换");
    JButton b2=new JButton("全部替换");
    JButton b3=new JButton("取消");
    JTextArea a1=new JTextArea();
    Font f1=new Font("隶书",Font.PLAIN,15);
    //int m;
    void set(JTextArea n){
        a1=n;
    }
    public Change(){
        setTitle("替换");
        setSize(500,300);
        setLocation(200,300);
        setLayout(new FlowLayout());
        l1.setFont(f1);
        l2.setFont(f1);
        t1.setFont(f1);
        t2.setFont(f1);
        b1.setFont(f1);
        b2.setFont(f1);
        b3.setFont(f1);
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(b1);
        add(b2);
        add(b3);
        b1.addActionListener(new MyActionListener3());
        b2.addActionListener(new MyActionListener3());
        b3.addActionListener(new MyActionListener3());
    }
    class MyActionListener3 implements ActionListener{

        public void actionPerformed(ActionEvent e2) {
            int m;
            String source=a1.getText();
            String find=t1.getText();
            String change=t2.getText();
            if(e2.getActionCommand()=="取消"){
                setVisible(false);
                t1.setText("");
                t2.setText("");
            }
            if(e2.getActionCommand()=="替换"){
                m=source.indexOf(find,0);
                String s1=source.substring(0,m);
                String s2=source.substring(m+find.length());
                source=s1+change+s2;
                if(m==-1){
                    JOptionPane.showMessageDialog(null,"对不起，没找到您要找的内容！");
                }else{
                    a1.setText(source);
                }
            }
            if(e2.getActionCommand()=="全部替换"){
                m=-change.length();
                while(true){
                    m=source.indexOf(find,m+change.length());
                    if(m==-1)
                        break;
                    else{
                        String s1=source.substring(0,m);
                        String s2=source.substring(m+find.length());
                        source=s1+change+s2;
                    }
                }
                a1.setText(source);
            }

        }
    }

}
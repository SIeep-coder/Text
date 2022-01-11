package com.Test03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Font1 extends JDialog implements ItemListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JComboBox comboBox1 = new JComboBox();
    JComboBox comboBox2 = new JComboBox();
    JComboBox comboBox3 = new JComboBox();
    JLabel lab1=new JLabel("字体：");
    JLabel lab2=new JLabel("字形：");
    JLabel lab3=new JLabel("字号：");
    String name=new String("宋体");
    Font f1=new Font("隶书",Font.PLAIN,15);
    int style=1;
    int size=12;
    String []array2=new String[]{"常       规","倾        斜","加       粗"};
    String []array3=new String[]{"14","15","15","16","17","18"};
    GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    String [] fontName=ge.getAvailableFontFamilyNames();
    JButton b1=new JButton("确定");
    JButton b2=new JButton("颜色");
    JTextArea a1=new JTextArea();
    void set(JTextArea n){
        a1=n;
    }
    public Font1(){
        setTitle("字体");
        setSize(500,600);
        setLayout(new FlowLayout());
        //panel1.setLocation(100, 200);
        lab1.setFont(f1);
        lab2.setFont(f1);
        comboBox1.setModel(new DefaultComboBoxModel(fontName));
        comboBox1.setFont(f1);
        for(int i=1;i<fontName.length;i++){
            //comboBox1.setSelectedIndex(i);
            comboBox1.setSelectedItem(fontName);
            //comboBox1.addItem(fontName);
        }
        comboBox2.setModel(new DefaultComboBoxModel(array2));
        comboBox2.setFont(f1);
        for(int i=1;i<array2.length;i++){
            //comboBox2.setSelectedIndex(i);
            comboBox2.setSelectedItem(array2);
            //comboBox2.addItem(array2);
        }
        comboBox3.setModel(new DefaultComboBoxModel(array3));
        comboBox3.setFont(f1);
        for(int i=1;i<array3.length;i++){
            //comboBox2.setSelectedIndex(i);
            comboBox2.setSelectedItem(array3);
            //comboBox3.addItem(array3);
        }

        panel1.add(lab1);
        panel1.add(comboBox1);
        panel2.add(lab2);
        panel2.add(comboBox2);
        panel3.add(lab3);
        panel3.add(comboBox3);
        panel3.add(b1);
        panel3.add(b2);
        b2.addActionListener(new MyActionListener3());
        comboBox1.addItemListener(this);
        comboBox2.addItemListener(this);
        comboBox3.addItemListener(this);
        b1.addActionListener(new MyActionListener3());
        add(panel1);
        add(panel2);
        add(panel3);
    }
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource()==comboBox1){
            name=comboBox1.getSelectedItem().toString();
        }
        if(e.getSource()==comboBox2){
            String s1=comboBox2.getSelectedItem().toString();
            if(s1.equals("加粗")){
                style=Font.BOLD;
            }
            if(s1.equals("倾斜")){
                style=Font.ITALIC;
            }
            if(s1.equals("常规")){
                style=Font.PLAIN;
            }
        }
        if(e.getSource()==comboBox3){
            String s2=comboBox3.getSelectedItem().toString();
            size=Integer.parseInt(s2);
        }

    }

    class MyActionListener3 implements ActionListener{
        public void actionPerformed(ActionEvent e2) {
            Font f=new Font(name,style,size);
            a1.setFont(f);
            if(e2.getActionCommand()=="颜色"){
                setcolor();
            }
        }
    }
    void setcolor(){
        Color	fontcolor=JColorChooser.showDialog(this,"字体颜色选择",a1.getForeground());
        a1.setForeground(fontcolor);
    }
}

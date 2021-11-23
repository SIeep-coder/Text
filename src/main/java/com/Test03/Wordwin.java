package com.Test03;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
public class Wordwin extends JFrame implements DocumentListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JMenuBar menubar=new JMenuBar();
    JMenu file=new JMenu("文件");
    JMenu edit=new JMenu("编辑");
    JMenu geshi=new JMenu("格式");
    JMenu look =new JMenu("查看");
    JMenu help =new JMenu("帮助");
    JTextArea wordArea=new JTextArea();
    JScrollPane imgScrollPane = new JScrollPane(wordArea);
    String [] str1={"新建","打开","保存","另存为","页面设置","打印","退出"};
    String [] str2={"剪切","复制","粘贴","查找","替换"};
    String [] str3={"自动换行","字体"};
    Font f1=new Font("隶书",Font.PLAIN,15);
    Search d1=new Search();
    Font1 z1=new Font1();
    Change c1=new Change();
    int flag=0;
    String source="";
    public static void main(String[] args) {
        JFrame wordwin=new Wordwin();
        wordwin.setVisible(true);
    }
    public Wordwin(){
        c1.set(wordArea);
        z1.set(wordArea);
        setTitle("文本编辑器");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();//获取屏幕分辨率
        setSize(screenSize.width/2,screenSize.height/2);//大小
        setLocation(screenSize.width/4,screenSize.height/4);//位置
        add(imgScrollPane,BorderLayout.CENTER);
        setJMenuBar(menubar);
        file.setFont(f1);
        edit.setFont(f1);
        geshi.setFont(f1);
        look.setFont(f1);
        help.setFont(f1);
        menubar.add(file);
        menubar.add(edit);
        menubar.add(geshi);
        menubar.add(look);
        menubar.add(help);
        wordArea.getDocument().addDocumentListener(this);
        for(int i=0;i<str1.length;i++){
            JMenuItem item1= new JMenuItem(str1[i]);
            item1.addActionListener(new MyActionListener1());
            item1.setFont(f1);
            file.add(item1);
        }
        for(int i=0;i<str2.length;i++){
            JMenuItem item2= new JMenuItem(str2[i]);
            item2.addActionListener(new MyActionListener1());
            item2.setFont(f1);
            edit.add(item2);
        }
        for(int i=0;i<str3.length;i++){
            JMenuItem item3= new JMenuItem(str3[i]);
            item3.addActionListener(new MyActionListener1());
            item3.setFont(f1);
            geshi.add(item3);
        }
    }
    public void changedUpdate(DocumentEvent e) {
        flag=1;
    }

    public void insertUpdate(DocumentEvent e) {
        flag=1;
    }
    public void removeUpdate(DocumentEvent e) {
        flag=1;
    }
    void open(){
        FileDialog  filedialog=new FileDialog(this,"打开",FileDialog.LOAD);
        filedialog.setVisible(true);
        String path=filedialog.getDirectory();
        String name=filedialog.getFile();
        if(path!=null && name!=null){
            FileInputStream file = null;
            try {
                file = new FileInputStream(path+name);
            } catch (FileNotFoundException e) {

            }
            InputStreamReader put =new InputStreamReader(file);
            BufferedReader in=new BufferedReader(put);
            String temp="";
            String now = null;
            try {
                now = (String)in.readLine();
            } catch (IOException e) {

                e.printStackTrace();
            }
            while(now!=null){
                temp+=now+"\r\n";
                try {
                    now=(String)in.readLine();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            wordArea.setText(temp);
        }

    }

    void save(){
        FileDialog  filedialog=new FileDialog(this,"保存",FileDialog.SAVE);
        filedialog.setVisible(true);
        if(filedialog.getDirectory()!=null && filedialog.getFile()!=null){
            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(new FileOutputStream(filedialog.getDirectory()+filedialog.getFile()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(wordArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag=0;
            try {
                out.close();
                source=wordArea.getText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void newfile(){
        if(flag==0){
            wordArea.setText("");
        }
        if(flag==1){
            int m=  JOptionPane.showConfirmDialog(this,"是否保存该文件");
            if(m==0){
                save();
                wordArea.setText("");
            }

            if(m==1){
                //System.exit(0);
                wordArea.setText("");
                flag=0;
            }
        }
    }
    void exit(){
        if(flag==0){
            System.exit(0);
        }
        if(flag==1){
            int m=  JOptionPane.showConfirmDialog(this,"是否保存该文件");
            if(m==0){
                save();
            }
            if(m==1){
                System.exit(0);
            }
        }
    }
    class MyActionListener1 implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()instanceof JMenuItem){
                if(e.getActionCommand()=="剪切"){
                    wordArea.cut();
                }
                if(e.getActionCommand()=="复制"){
                    wordArea.copy();
                }
                if(e.getActionCommand()=="粘贴"){
                    wordArea.paste();
                }
                if(e.getActionCommand()=="查找"){
                    d1.setVisible(true);
                }
                if(e.getActionCommand()=="字体"){
                    z1.setVisible(true);
                }
                if(e.getActionCommand()=="替换"){
                    c1.setVisible(true);
                }
                if(e.getActionCommand()=="打开"){
                    open();
                }
                if(e.getActionCommand()=="保存"){
                    save();
                }
                if(e.getActionCommand()=="新建"){
                    newfile();
                }
                if(e.getActionCommand()=="退出"){
                    exit();
                }
            }

        }

    }
}
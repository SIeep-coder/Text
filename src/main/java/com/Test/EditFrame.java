package com.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFrame extends JFrame {
    // TODO 自动生成的构造函数存根
    boolean saveFlag = false;
    File saveFileRoot = null;
    JFrame jFrame;
    JPanel jPanelSouth;
    JMenuBar jMenuBar1;
    JMenu jMenu1;
    JMenuItem jMenuItem1;
    JMenuItem jMenuItem2;
    JMenuItem jMenuItem3;
    JMenuItem jMenuItem4;
    JSeparator jSeparator1;
    JTextArea jTextArea;
    JScrollPane scrollPane;// 滚动条
    public EditFrame() {
        // TODO 自动生成的构造函数存根
        jFrame = new JFrame("WJL-文本编辑器");
        jPanelSouth = new JPanel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu("文件");
        jMenuItem1 = new JMenuItem("打开");
        jMenuItem2 = new JMenuItem("保存");
        jMenuItem3 = new JMenuItem("另存为");
        jMenuItem4 = new JMenuItem("退出");
        jSeparator1 = new JSeparator();
        jTextArea = new JTextArea();
        scrollPane = new JScrollPane(jTextArea);
        jFrame.setSize(800, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(false);

        setLayout();
        setSouthPanel();
        // set relationship for your component
        setRelationShip();
        // 设置 scrollPane for TextArea
        setScscrollPane();
        iniClick();
    }
    private void setRelationShip() {
        jFrame.add(BorderLayout.CENTER, scrollPane);
        jFrame.add(BorderLayout.SOUTH, jPanelSouth);
        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);
        jMenu1.add(jMenuItem4);
        jMenuBar1.add(jMenu1);
        jFrame.setJMenuBar(jMenuBar1);
    }
    private void setLayout() {
        GridLayout gridLayout = new GridLayout(1, 2);
        jPanelSouth.setLayout(gridLayout);
    }
    private void setScscrollPane() {
// jTextArea.setLineWrap(true);// 设置满一行自动换行
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    private void setSouthPanel() {
        // add time for SouthPanel
        JLabel jLabelDate = new JLabel("Date");
        JLabel jLabelTime = new JLabel("Time");
        Timer timeAction = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式
                SimpleDateFormat date = new SimpleDateFormat("yyyy 年 MM 月 dd 日 ");
                jLabelDate.setText("  当前日期： " + date.format(new Date(timemillis)));
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss ");
                jLabelTime.setText("  当前时间： " + time.format(new Date(timemillis)));
            }
        });
        jPanelSouth.add(jLabelDate);
        jPanelSouth.add(jLabelTime);
        timeAction.start();
    }
    private void iniClick() {
        jFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                int x = JOptionPane.showConfirmDialog(null, "确认退出么？", "友情提示", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (x == 0) {
                    System.exit(0);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub
            }
        });
        jMenuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int x = JOptionPane.showConfirmDialog(null, "确认退出么？", "友情提示", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (x == 0) {
                    System.exit(0);
                }
            }
        });
        jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                FileReadThread fileReadThread = new FileReadThread(EditFrame.this);// 开启文件读取线程
                fileReadThread.start();
                System.out.println(saveFileRoot);
                saveFlag = true;
                jTextArea.setText("");
            }
        });
        jMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Save save = new Save(EditFrame.this);
                save.start();
                saveFlag = true;
            }
        });
        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (!saveFlag) {
                    Save save = new Save(EditFrame.this);
                    save.start();
                    saveFlag = true;
                } else {
                    new Save(EditFrame.this, saveFileRoot);
                }
            }
        });
    }
    public JTextArea getjTextArea() {
        return jTextArea;
    }
    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    public File getSaveFileRoot() {
        return saveFileRoot;
    }
    public void setSaveFileRoot(File saveFileRoot) {
        this.saveFileRoot = saveFileRoot;
    }
    public JFrame getjFrame() {
        return jFrame;
    }
    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }
}
package com.Test;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class FileReadThread extends Thread {
    private EditFrame test;
    public FileReadThread(EditFrame test ) {
        this.test = test;
    }
    @Override
    public void run() {
        JFileChooser chooser = new JFileChooser("d:/");
        chooser.setFileFilter(new FileFilter() {// 定义文件过滤器,仅显示文件夹和txt文本
            @Override
            public String getDescription() {
                return null;
            }
            @Override
            public boolean accept(File file) {
                if (file.isDirectory() || file.getName().endsWith(".txt"))
                    return true;
                return false;
            }
        });
        int option = chooser.showOpenDialog(test);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selFile = chooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selFile), "gbk"));
                test.setSaveFileRoot(selFile);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    test.getjTextArea().append(line + "\n");
                    Thread.sleep(30);// 线程暂停,以看到读取过程效果
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(test, "读取完毕");
        }
    }
}
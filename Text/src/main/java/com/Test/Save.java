package com.Test;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Save extends Thread {
    private EditFrame area;
    private File saveFileRoot = null;
    public Save(EditFrame area, File saveFileRoot) {
        System.out.println(saveFileRoot + "123");
        String text = area.getjTextArea().getText();
        String[] lines = text.trim().split("\n");
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(saveFileRoot), true);
            for (String line : lines)
                out.println(line);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Save(EditFrame area) {
        this.area = area;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt");
            }
            @Override
            public String getDescription() {
                return "SAVE TO";
            }
        });
        int r = chooser.showSaveDialog(area);
        if (r != JFileChooser.APPROVE_OPTION)
            return;
        File f = chooser.getSelectedFile();
        area.setSaveFileRoot(f);
        String text = area.getjTextArea().getText();
        String[] lines = text.trim().split("\n");
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(f), true);
            for (String line : lines)
                out.println(line);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public File getSaveFileRoot() {
        return saveFileRoot;
    }
    public void setSaveFileRoot(File saveFileRoot) {
        this.saveFileRoot = saveFileRoot;
    }
}



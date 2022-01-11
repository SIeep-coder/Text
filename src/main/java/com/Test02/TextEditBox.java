package com.Test02;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class TextEditBox extends JFrame {

    private static final long serialVersionUID = 1L;
    private boolean isChanged = false;
    private File file = null;

    private JMenuBar bar = null;
    private JMenu dJMenu = null;
    private JMenu eJMenu = null;
    private JMenu aJMenu = null;
    private JMenu cJMenu = null;
    private JMenu encodeItem = null;
    private JMenu decodeItem = null;
    private JMenuItem openItem = null;
    private JMenuItem saveItem = null;
    private JMenuItem saveAsItem = null;
    private JMenuItem closeItem = null;
    private JMenuItem undoItem = null;
    private JMenuItem redoItem = null;
    private JMenuItem cutItem = null;
    private JMenuItem copyItem = null;
    private JMenuItem pasteItem = null;
    private JMenuItem findItem = null;
    private JMenuItem replaceItem = null;
    private JRadioButton en_utf_8_Item = null;
    private JRadioButton en_gbk_Item = null;
    private JRadioButton de_utf_8_Item = null;
    private JRadioButton de_gbk_Item = null;
    private JMenuItem aboutItem = null;

    private JFileChooser jfc = null;
    private JTextArea ta = null;
    private JScrollPane scrollPane = null;
    private JPanel jDown = null;
    private JLabel labelLeft = null;
    private JLabel labelCenter = null;
    private JLabel labelRight = null;


    //编码格式
    private String encode = "UTF-8";
    private String decode = "UTF-8";

    // ctrl键是否按下
    private boolean key_ctrl = false;
    // 是否在打开文件
    private boolean isOpen = false;
    // 系统滚动条
    private MouseWheelListener sysWheel;
    // 文本域字体大小
    Font f = new Font("Serif", 0, 28);
    // 撤销的监听器
    UndoManager um = new UndoManager();

    public TextEditBox() {
        // 初始化jfc(文件选择器)
        jfc = new JFileChooser();
    }

    // 内部类
    private class event extends MouseAdapter {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.isControlDown()) {// 当ctrl键被按下，滚动为放大缩小
                // System.out.println(e.getWheelRotation());
                if (e.getWheelRotation() < 0 && f.getSize() < 60) {// 如果滚动条向前就放大文本
                    f = new Font(f.getFamily(), f.getStyle(), f.getSize() + 1);
                    ta.setFont(f);
                } else if (e.getWheelRotation() > 0 && f.getSize() > 0) {// 滚动条向后就缩小文本
                    f = new Font(f.getFamily(), f.getStyle(), f.getSize() - 1);
                    ta.setFont(f);
                }
                labelCenter.setText("字体大小：" + f.getSize());
            } else {// 当ctrl没有被按下，则为系统滚动
                scrollPane.addMouseWheelListener(sysWheel);
                sysWheel.mouseWheelMoved(e);// 触发系统滚动事件。
                scrollPane.removeMouseWheelListener(sysWheel);
            }
        }
    }

    // 内部类
    private class change implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changed();
        }

        public void changed() {
            if(!isOpen) {
                isChanged = true;
                labelLeft.setText("修改状态：已修改");
                // 统计字数
                labelRight.setText("字数：" + tools.replaceBlank(ta.getText()));
            }
        }
    }

    //重新打开读取
    public void openagainDialog() {
        isOpen = true;
        BufferedReader br = null;
        if (file != null) {
            String str = "";
            try {
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(file),decode);
                br = new BufferedReader( fReader);
                ta.setText("");
                str = br.readLine();
                while (str != null) {
                    ta.append(str + '\n');
                    str = br.readLine();
                }
            } catch (FileNotFoundException e1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                isOpen = false;
                isChanged = false;
                labelLeft.setText("状态：已打开");
                this.setTitle(file.getName());
                labelRight.setText("字数：" + tools.replaceBlank(ta.getText()));
            }
        }
    }
    // 打开文件读取对话框的方法，打开控件调用此方法
    public void openOpenDialog() {
        isOpen = true;
        int status = jfc.showOpenDialog(TextEditBox.this);
        BufferedReader br = null;
        if (status == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
            String str = "";
            try {
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(file), decode);
                br = new BufferedReader(fReader);
                ta.setText("");
                str = br.readLine();
                while (str != null) {
                    ta.append(str + '\n');
                    str = br.readLine();
                }
            } catch (FileNotFoundException e1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                isOpen = false;
                isChanged = false;
                labelLeft.setText("状态：已打开");
                this.setTitle(file.getName());
                labelRight.setText("字数：" + tools.replaceBlank(ta.getText()));
            }
        }
    }

    // 打开文件保存对话框的方法，保存和另存为时调用此方法
    public void openSaveDialog() {
        int status = jfc.showSaveDialog(TextEditBox.this);
        BufferedWriter bw = null;
        if (status == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
            try {
                OutputStreamWriter fWriter = new OutputStreamWriter(new FileOutputStream(file),encode);
                bw = new BufferedWriter(fWriter);
                String[] strs = ta.getText().split("\n");
                for (String str : strs) {
                    bw.write(str);
                    bw.newLine();
                    bw.flush();
                }
            } catch (FileNotFoundException e1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                isChanged = false;
                labelLeft.setText("状态：已保存");
                this.setTitle(file.getName());
            }
        }
    }

    // 不打开文件保存对话框直接按照当前文件保存的方法
    public void onlySave() {
        BufferedWriter bw = null;
        try {
            OutputStreamWriter fWriter = new OutputStreamWriter(new FileOutputStream(file),encode);
            bw = new BufferedWriter(fWriter);
            String[] strs = ta.getText().split("\n");
            for (String str : strs) {
                bw.write(str);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            isChanged = false;
            labelLeft.setText("状态：已保存");
            this.setTitle(file.getName());
        }
    }

    // 判断按钮是否可点击
    public void ifClick() {
        if (um.canUndo()) {
            undoItem.setEnabled(true);
        } else {
            undoItem.setEnabled(false);
        }
        if (um.canRedo()) {
            redoItem.setEnabled(true);
        } else {
            redoItem.setEnabled(false);
        }
    }

    //查找类
    class Find extends Frame {
        JButton findbutton = new JButton("查找");
        JButton nextbutton = new JButton("下一个");
        TextField findText = new TextField();


        private String oldText;
        private String newText;
        int n1=0,n2=0;


        Find(){
//            System.out.println("f构造");
            JFrame f = new JFrame("查找");
            f.setSize(300, 200);
            f.setLocation(300, 300);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLayout(null);


            findText.setBounds(40,40,220,35);
            findbutton.setBounds(40,100,80,40);
            nextbutton.setBounds(180,100,80,40);
            findbutton.addActionListener(new newlistener());
            nextbutton.addActionListener(new newlistener());

            f.add(findText);
            f.add(findbutton);
            f.add(nextbutton);
//            System.out.println("f构造结束");
        }




        class newlistener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {

                oldText="";
                newText="";
                oldText += ta.getText();
                newText +=findText.getText();

                //输入 e. 查看源码.
                if (e.getSource() == findbutton) {

                    n1=oldText.indexOf(newText);
                    if (n1 < 0 ) {
                        JOptionPane.showMessageDialog(null,"没有");
                    }
                    else {
                        n2 = n1 + newText.length();
                        ta.setSelectionStart(n1);//选中从第n1个开始到第n2个结束
                        ta.setSelectionEnd(n2);
                        ta.requestFocus();
                        //System.out.println(n1);
                    }

                }

                if (e.getSource() == nextbutton) {
                    //System.out.println(456);
                    oldText = oldText.substring(n2);
                    //System.out.println(n2);
                    //System.out.println(oldText);
                    n1 = oldText.indexOf(newText);
                    if (n1 <0 ) {
                        JOptionPane.showMessageDialog(null,"没有了");
                    }
                    else {
                        int temp = n2;
                        n2 = n1 + newText.length() + n2;
                        n1 = n1+temp;
                        ta.setSelectionStart(n1);//选中从第n1个开始到第n2个结束
                        ta.setSelectionEnd(n2);
                        ta.requestFocus();
                    }

                }
            }


        }

    }

    //replace
    class Replace extends Frame {
        TextField findText = new TextField();
        TextField replaceText = new TextField();
        JLabel l1=new JLabel("查找内容");
        JLabel l2=new JLabel("替换为");
        Button findbutton = new Button("替换");
        Button allbutton = new Button("全部替换");


        private String oldText;
        private String newText;
        int n1=0,n2=0;
        int end = ta.getText().length();

        Replace(){
//            System.out.println("进行了r无参构造");
            JFrame r = new JFrame("替换");
            r.setSize(400, 250);
            r.setLocation(300, 300);
            r.setVisible(true);
            r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            r.setLayout(null);

            l1.setBounds(40,40,70,30);
            l2.setBounds(40,100,70,30);
            findText.setBounds(120,40,220,35);
            replaceText.setBounds(120,100,220,35);
            findbutton.setBounds(70,150,80,40);
            allbutton.setBounds(250,150,80,40);
            findbutton.addActionListener(new newlistener());
            allbutton.addActionListener(new newlistener());

            r.add(findText);
            r.add(replaceText);
            r.add(findbutton);
            r.add(allbutton);
            r.add(l1);
            r.add(l2);
//            System.out.println("r结束");
        }

        class newlistener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {

                oldText="";
                newText="";
                oldText += ta.getText();
                newText +=findText.getText();

                //输入 e. 查看源码.
                if (e.getSource() == findbutton) {

                    n1=oldText.indexOf(newText);
                    if (n1 < 0 ) {
                        JOptionPane.showMessageDialog(null,"没有");
                    }
                    else {
                        n2 = n1 + newText.length();
                        ta.setSelectionStart(n1);//选中从第n1个开始到第n2个结束
                        ta.setSelectionEnd(n2);
                        String temp1 = ta.getText().substring(0,n1);
                        String temp2 = replaceText.getText();
                        String temp3 = ta.getText().substring(n2,end);
                        ta.setText(temp1+temp2+temp3);
                        //System.out.println(n1);
                    }

                }

                if (e.getSource() == allbutton) {

                    while( true ){
                        oldText = ta.getText();
                        n1 = oldText.indexOf(newText);
                        if (n1 < 0 ) {
                            JOptionPane.showMessageDialog(null,"替换完成");
                            break;
                        }else {
                            n2 = n1 + newText.length();
                            String temp1 = ta.getText().substring(0,n1);
                            String temp2 = replaceText.getText();
                            String temp3 = ta.getText().substring(n2,end);
                            ta.setText(temp1+temp2+temp3);
                            end = ta.getText().length();
                        }

                    }

                }
            }


        }

    }

    // 全局按键事件监听
    public void addKeyListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (((KeyEvent) event).getID() == KeyEvent.KEY_RELEASED) {
                    int key = ((KeyEvent) event).getKeyCode();
                    // System.out.println(key);
                    if (key == 17) {
                        key_ctrl = false;
                    }
                }
                if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
                    int key = ((KeyEvent) event).getKeyCode();
                    // System.out.println(key);
                    if (key == 17) {
                        key_ctrl = true;
                    }
                    //Find
                    if (key == 70&& key_ctrl == true) {
                        System.out.println("ctrl+f");
                        Find f = new Find();

                    }
                    //Replace
                    if (key == 71&& key_ctrl == true) {
                        System.out.println("ctrl+g");
                        Replace r = new Replace();
                    }

                    if (key == 83 && key_ctrl == true) {
                        System.out.println("ctrl+s");
                        if (file == null) {
                            openSaveDialog();
                        } else {
                            onlySave();
                        }
                    }
                    if (key == 90 && key_ctrl == true) {
                        System.out.println("ctrl+z");
                        if (um.canUndo()) {// 撤销
                            um.undo();
                        }
                    }
                    if (key == 89 && key_ctrl == true) {
                        System.out.println("ctrl+y");
                        if (um.canRedo()) {// 恢复
                            um.redo();
                        }
                    }

                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    // 添加Bar各个组件的监听器
    public void addBarListener() {
        // 菜单栏焦点事件
        eJMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuCanceled(MenuEvent arg0) {
                menuSelected(arg0);
            }

            @Override
            public void menuDeselected(MenuEvent arg0) {
                menuSelected(arg0);
            }

            @Override
            public void menuSelected(MenuEvent arg0) {
                ifClick();

            }
        });
        // 关于NotePad触发监听器
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TextEditBox.this, "在此有关于本文本编辑器的详细介绍……", e.getActionCommand(),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 编辑栏的撤销、恢复、剪切、复制、粘贴监听器
        undoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println((um.canUndo() ? "可" : "不可") + "撤销");
                if (um.canUndo()) {// 撤销
                    um.undo();
                }
                ifClick();
            }
        });
        redoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println((um.canRedo() ? "可" : "不可") + "恢复");
                if (um.canRedo()) {// 恢复
                    um.redo();
                }
                ifClick();
            }
        });
        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.cut();
            }
        });
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.copy();
            }
        });
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.paste();
            }
        });






        findItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Find f = new Find();
            }
        });

        replaceItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Replace f = new Replace();
            }
        });















        //格式栏的编码和解码
        en_utf_8_Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encode = "UTF-8";
            }
        });
        en_gbk_Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encode = "GBK";
            }
        });
        de_utf_8_Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("utf-8");
                decode = "UTF-8";
                openagainDialog();
            }
        });
        de_gbk_Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("gbk");
                decode = "GBK";
                openagainDialog();
            }
        });

        // 打开按键（如果文件没修改直接调用读取文件的文件选择对话框，修改了的话弹出提示框提示是否保存再进一步操作）
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isChanged == false) {
                    openOpenDialog();
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存", "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候调用保存文件的文件选择对话框,否的时候直接打开读取文件的文件选择对话框
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        openOpenDialog();
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存到" + file.getPath(), "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候直接保存,否的时候打开读取文件的文件选择对话框
                    if (result == JOptionPane.YES_OPTION) {
                        onlySave();
                    } else if (result == JOptionPane.NO_OPTION) {
                        openOpenDialog();
                    }
                }
            }
        });

        // 另存为按键
        saveAsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSaveDialog();
            }
        });

        // 保存按钮监听
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    openSaveDialog();
                } else {
                    onlySave();
                }
            }
        });

        // 关闭的控件
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 如果修改未保存弹出对话框，保存了则直接退出
                if (isChanged == false) {
                    System.exit(0);
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存", "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候调用保存文件的文件选择对话框,否的时候直接退出
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存到" + file.getPath(), "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候直接保存,否的时候直接退出
                    if (result == JOptionPane.YES_OPTION) {
                        onlySave();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });

    }

    // 窗口的监听器
    public void myaddWindowListener() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // 如果修改未保存弹出对话框，保存了则直接退出
                if (isChanged == false) {
                    System.exit(0);
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存", "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候调用保存文件的文件选择对话框,否的时候直接退出
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEditBox.this, "文件还未保存，是否保存到" + file.getPath(), "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    // 选择是的时候直接保存,否的时候直接退出
                    if (result == JOptionPane.YES_OPTION) {
                        onlySave();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });
    }

    // 加载文件菜单
    public void launchBar() {
        bar = new JMenuBar();
        dJMenu = new JMenu("文本");
        eJMenu = new JMenu("编辑");
        cJMenu = new JMenu("格式");
        aJMenu = new JMenu("关于");

        openItem = new JMenuItem("打开");
        saveItem = new JMenuItem("保存(ctrl+s)");
        saveAsItem = new JMenuItem("另存为");
        closeItem = new JMenuItem("关闭");

        undoItem = new JMenuItem("撤销(ctrl+z)");
        redoItem = new JMenuItem("恢复(ctrl+y)");
        cutItem = new JMenuItem("剪切(ctrl+x)");
        copyItem = new JMenuItem("复制(ctrl+c)");
        pasteItem = new JMenuItem("粘贴(ctrl+v)");
        findItem = new JMenuItem("查找(ctrl+f)");
        replaceItem = new JMenuItem("替换(ctrl+g)");


        encodeItem = new JMenu("编码");
        decodeItem = new JMenu("解码");
        en_utf_8_Item = new JRadioButton("UTF-8",true);
        en_gbk_Item = new JRadioButton("GBK",false);
        de_utf_8_Item = new JRadioButton("UTF-8",true);
        de_gbk_Item = new JRadioButton("GBK",false);
        //按钮分组
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        group1.add(en_utf_8_Item);
        group1.add(en_gbk_Item);
        group2.add(de_utf_8_Item);
        group2.add(de_gbk_Item);

        aboutItem = new JMenuItem("关于本编辑器");

        setJMenuBar(bar);
        bar.add(dJMenu);
        bar.add(eJMenu);
        bar.add(cJMenu);
        bar.add(aJMenu);

        dJMenu.add(openItem);
        dJMenu.add(saveItem);
        dJMenu.add(saveAsItem);
        dJMenu.add(closeItem);
        eJMenu.add(undoItem);
        eJMenu.add(redoItem);
        eJMenu.add(cutItem);
        eJMenu.add(copyItem);
        eJMenu.add(pasteItem);
        eJMenu.add(findItem);
        eJMenu.add(replaceItem);
        cJMenu.add(encodeItem);
        cJMenu.add(decodeItem);
        aJMenu.add(aboutItem);

        encodeItem.add(en_utf_8_Item);
        encodeItem.add(en_gbk_Item);
        decodeItem.add(de_utf_8_Item);
        decodeItem.add(de_gbk_Item);
    }

    // 加载文本域
    public void launchTextArea() {
        ta = new JTextArea();
        ta.setFont(f);
        // 自动换行
        ta.setLineWrap(true);
        // 文本编辑了则触发监听器设置isChanged变量为true
        // 监听状态改变
        ta.getDocument().addDocumentListener(new change());
        // 监听撤销
        ta.getDocument().addUndoableEditListener(new UndoableEditListener() {// 注册撤销可编辑监听器
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
        });// 编辑撤销的监听
        // 文本域滚动条
        scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sysWheel = scrollPane.getMouseWheelListeners()[0];// 得到系统滚动事件
        scrollPane.removeMouseWheelListener(sysWheel);// 移除系统滚动，需要时添加
        scrollPane.addMouseWheelListener(new event());
        add(scrollPane, BorderLayout.CENTER);
    }

    // 加载Label的板块
    public void launchLabel() {
        jDown = new JPanel(); // Label的板块
        labelLeft = new JLabel("修改状态：未修改");
        labelCenter = new JLabel("字体大小：" + f.getSize());
        labelRight = new JLabel("字数：" + tools.replaceBlank(ta.getText()));
        jDown.setLayout(new GridLayout(1, 5));
        jDown.add(labelLeft);
        jDown.add(labelCenter);
        jDown.add(labelRight);
        add(jDown, BorderLayout.SOUTH);
    }

    // 加载整个窗口
    public void launchFrame() {
        setBounds(20, 20, 1000, 800);
        setTitle("WJL——文本编辑器");
        setLayout(new BorderLayout());


        // 加载文件菜单
        this.launchBar();
        // 加载文本域
        this.launchTextArea();
        // 加载Label的板块
        this.launchLabel();
        // 按钮组件监听
        this.addBarListener();
        // 全局按键事件监听
        this.addKeyListener();
        // 窗口的监听器
        this.myaddWindowListener();

        setVisible(true);
        // 设置JFram不要默认关闭，为了用对话框触发退出
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}


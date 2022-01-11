package Test;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JTextField;

public class Test {

    public static void main(String[] args) {

        new TestingFile();

    }

}

class TestingFile extends JFrame

{

    public TestingFile()

    {
        JFrame jf = new JFrame("TextField案例");
        this.setVisible(true);

        this.setSize(600,600);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = jf.getContentPane();
        contentPane.setLayout(new BorderLayout());


        JTextField text=new JTextField("这是一个文本框",20);

        JButton button=new JButton("清空文本");

        text.setSize(30, 30);


        jf.add(text);
        jf.add(button);


        button.addActionListener(new ActionListener()

        {

            @Override

            public void actionPerformed(ActionEvent e) {

                text.setText("已完成清空处理！");

            }

        });

    }

}
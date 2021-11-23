//// 确定用户按下打开按钮，而非取消按钮
//
//if (option != JFileChooser.APPROVE_OPTION)
//
//        return;
//
//// 获取用户选择的文件对象
//
//        file = chooser.getSelectedFile();
//
//// 显示文件信息到文本框
//
//        fileField.setText(file.toString());
//
//        }
//
///**
//
// * 替换按钮的事件处理方法
//
// *
//
// * @param e
//
// */
//
//protected void do_replaceButton_actionPerformed(ActionEvent event) {
//
//        String searchText = searchTextField.getText();// 获取搜索文本
//
//        String replaceText = replaceTextField.getText();// 获取替换文本
//
//        if (searchText.isEmpty())
//
//        return;
//
//        try {
//
//        FileReader fis = new FileReader(file);// 创建文件输入流
//
//        char[] data = new char[1024];// 创建缓冲字符数组
//
//        int rn = 0;
//
//        StringBuilder sb = new StringBuilder();// 创建字符串构建器
//
//        while ((rn = fis.read(data)) > 0) {// 读取文件内容到字符串构建器
//
//        String str = String.valueOf(data, 0, rn);
//
//        sb.append(str);
//
//        }
//
//        fis.close();// 关闭输入流
//
//// 从构建器中生成字符串，并替换搜索文本
//
//        String str = sb.toString().replace(searchText, replaceText);
//
//        FileWriter fout = new FileWriter(file);// 创建文件输出流
//
//        fout.write(str.toCharArray());// 把替换完成的字符串写入文件内
//
//        fout.close();// 关闭输出流
//
//        } catch (FileNotFoundException e) {
//
//        e.printStackTrace();
//
//        } catch (IOException e) {
//
//        e.printStackTrace();
//
//        }
//
//        JOptionPane.showMessageDialog(null, "替换完成");
//
//        }
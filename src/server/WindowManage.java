package server;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class WindowManage extends JFrame{
	static private Container c = null;
	public WindowManage() throws Exception {
		c = getContentPane();
		Font f = new Font("黑体", 0, 20);
		setBounds(500, 100, 700, 600);
		c.setBackground(Color.WHITE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("服务端");
		JButton update = new JButton("更新好友");
		JButton save = new JButton("保存用户");
		JButton load = new JButton("读取用户");
		JButton send = new JButton("发送公告");
		
		DefaultListModel iltems = new DefaultListModel();
//		iltems.addElement("1");
//		iltems.addElement("1");
//		iltems.addElement("1");
		JList userList = new JList(iltems);
		JTextArea jtw=new JTextArea();
		JScrollPane js=new JScrollPane(userList);
		JScrollPane jsw=new JScrollPane(jtw);
		update.setBounds(500, 40, 150, 80);
		save.setBounds(500, 170, 150, 80);
		load.setBounds(500, 300, 150, 80);
		send.setBounds(500, 430, 150, 80);
		js.setBounds(50,40, 400, 340);
		jsw.setBounds(50,430, 400, 80);
		c.add(update);
		c.add(save);
		c.add(load);
		c.add(send);
		c.add(js);
		c.add(jsw);
		setVisible(true);
		 
	}
	public static void main(String[] args) throws Exception {
		new WindowManage();
	}
}

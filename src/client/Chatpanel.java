package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Chatpanel extends JFrame {
	static private Container c = null;
	static private String Uname = null;

	public Chatpanel() throws Exception {
		c = getContentPane();
		setBounds(1150, 80, 250, 700);
		c.setBackground(Color.WHITE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(null);
		 
	}

	public void start() {
		final JTextField username = new JTextField("请输入用户名", 20);
		username.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (username.getText().length() == 0) {
					username.setText("请输入用户名");
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if ("请输入用户名".equals(username.getText())) {
					username.setText(null);
				}
			}
		});
		final JPasswordField pwd = new JPasswordField(15);
		final JTextField password = new JTextField("请输入密码", 15);
		pwd.setVisible(false);
		password.setVisible(true);
		pwd.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (pwd.getPassword().length == 0) {
					pwd.setVisible(false);
					password.setVisible(true);
				} else {
					pwd.setVisible(true);
					password.setVisible(false);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				pwd.setText(null);
			}
		});

		password.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				pwd.setVisible(true);
				pwd.requestFocus();
				password.setVisible(false);

			}
		});

		JButton jb = new JButton("登陆");
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Link.pw.println("lg:"+username.getText()+"@"+Arrays.toString(pwd.getPassword()));
				Uname = username.getText();
			}
		});
		JButton signin = new JButton("注册");
		signin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Link.pw.println("sg:"+username.getText()+"@"+Arrays.toString(pwd.getPassword()));
			}
		});
		username.setBounds(75, 270, 100, 25);
		pwd.setBounds(75, 300, 100, 25);
		password.setBounds(75, 300, 100, 25);
		jb.setBounds(75, 350, 100, 25);
		signin.setBounds(75, 380, 100, 25);
		c.add(username);
		c.add(pwd);
		c.add(password);
		c.add(jb);
		c.add(signin);
		setVisible(true);
		update(getGraphics());
	}

	static private List<String> list = null;
	static private DefaultListModel iltems = null;

	public void run() {
		c.removeAll();
		c.setLayout(null);
		list = new LinkedList<String>();
		iltems = new DefaultListModel();
		addFri("群聊");
		Font f = new Font("黑体", 0, 20);
		JLabel userN = new JLabel(Uname);
		userN.setFont(f);
		userN.setBounds(10, 0, 180, 100);
		userN.setHorizontalAlignment(SwingConstants.RIGHT);
		userN.setVisible(true);
		final JList lst = new JList(iltems);
		lst.setFont(f);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = lst.locationToIndex(e.getPoint());
					WinManage.create(list.get(index));
				}
			}
		};
		lst.addMouseListener(mouseListener);
		JScrollPane js = new JScrollPane(lst);
		js.setBounds(5, 80, 220, 500);
		c.add(js);
		c.add(userN);
		setVisible(true);
		update(getGraphics());
	}

	public static synchronized void addFri(String name) {
		list.add(name.trim());
		iltems.addElement(name.trim());
	}
	public static synchronized void removeFri(String name){
		int index =list.indexOf(name);
		list.remove(index);
		iltems.remove(index);
		WinManage.close(name);
	}
	public static synchronized void removeall(){
		if(list!=null){
			list.clear();
		}
		if(iltems!=null){
			iltems.clear();
		}
		addFri("群聊");
	}

}

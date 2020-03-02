package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
/**
 * 该类用于创建聊天窗口
 * @author TYXFDZ
 *
 */
public class Chatwin extends JDialog{
	private Container c=null;
	static int x=500;
	static int y=100;
	private JTextArea jtr=null;
	
	
	public Chatwin(final String str){
		
	
		setTitle(str);
		c=getContentPane();
		setBounds(x, y, 400, 450);
		c.setBackground(Color.WHITE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		Font f=new Font("宋体",0,18);
		jtr=new JTextArea();
		jtr.setFont(f);
		jtr.setEditable(false);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				WinManage.wins.remove(str);
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
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		

		
		final JSplitPane h=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		h.setDividerLocation(250);
		
		JScrollPane js=new JScrollPane(jtr);
		
		h.setLeftComponent(js);
		
		
		
		final JSplitPane hJB=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		hJB.setDividerLocation(100);
		final JTextArea jtw=new JTextArea();
		jtw.setFont(f);
		hJB.setLeftComponent(jtw);
		JButton jb=new JButton("发送(Alt+S)");
		
		class Sendmessage{
			void send(){
				if(jtw.getText().trim().length()!=0){
					if(!"群聊".equals(getTitle())){
						receive("我:\n    "+jtw.getText().trim());
					}
					try {
						Link.pw.println("to:"+getTitle()+"@"+jtw.getText().trim());
					} catch (Exception e) {
						receive("发送失败");
					}
					jtw.setText(null);
				}else{
					jtw.setText(null);
				}
			}
		};
		final Sendmessage sm=new Sendmessage();
		jtw.addKeyListener(new KeyListener() {
			boolean alt=false;
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(KeyEvent.VK_ALT==e.getKeyCode()){
					alt=false;
				}
				if(KeyEvent.VK_ESCAPE==e.getKeyCode()){
					setVisible(false);
				}
//				if(KeyEvent.VK_ENTER==e.getKeyCode()){
//					jtw.setText(null);
//				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(KeyEvent.VK_ALT==e.getKeyCode()){
					alt=true;
				}
				if(KeyEvent.VK_S==e.getKeyCode()&&alt){
					sm.send();
					alt=false;
				}
//				if(KeyEvent.VK_ENTER==e.getKeyCode()){
//					sm.send();
//				}
				
				
			}
		});
		
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.send();

			}
		});
		JLabel JL=new JLabel();
		JL.setLayout(new FlowLayout());
		JL.add(jb);
		
		
		hJB.setRightComponent(JL);
		h.add(hJB);
		add(h);
		
		setVisible(true);
		update(getGraphics());
		
	}
	/*
	 * 用于接收信息显示在聊天窗口
	 */
	public void receive(String str){
		if(jtr!=null&&str.trim().length()>0){
			jtr.insert(str.trim()+"\n", jtr.getText().length());
			jtr.setCaretPosition(jtr.getText().length());
		}
	}
}
/**
 * 该类用于管理聊天窗口
 * @author TYXFDZ
 *
 */
class WinManage{
	static Map<String,Chatwin> wins=new HashMap<String,Chatwin>();
	static Chatwin win=null;

	/*
	 * 用于点击点击好友发起会话
	 */
	public static synchronized Chatwin create(String name){
		//检索map，如果不存在，则创建；
		//如果存在，则改关闭方式为不可见
		
		if(wins.containsKey(name.trim())){
			win=wins.get(name);
			
		}else{
			win=new Chatwin(name.trim());
			wins.put(name.trim(), win);
		}
		win.setVisible(true);
		return win;
	}
	/*
	 * 用于对方下线
	 */
	public static synchronized void close(final String name){
		//如果窗口是不可见的，则直接关闭，并且将之移除map。
		//如果可见，则设置关闭方式为关闭，并在关闭后再移除map。
		if(wins.containsKey(name)){
			win=wins.get(name);
			win.receive("对方已下线");
		}
	}
	/*
	 * 用于对方发来信息
	 */
	public static synchronized void receive(String name,String message){
		//如果在线。。
		//如果不在线，则创建之后发送
		win=create(name);
		
		if("群聊".equals(name)){
			name=message.substring(0, message.indexOf("@"));
			message=message.substring(name.length()+1);
		}
		win.receive(name+"\n    "+message);
		
	}
	

}

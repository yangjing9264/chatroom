package client;

import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MessageSift {
	/**
	 * 有以下几种从服务器发来的信息
	 * 
	 * 1系统信息   弹窗！
	 * 	1）注册成功：成功；失败 signin：succeed fail  
	 * 	2）登陆成功：成功；失败：用户名错误；密码错误login succeed wrongname wrongpwd
	 * 
	 * 2好友状态信息  刷新好友列表  调用Chatpanel的addFri、removeFri
	 * 	1）当前在线好友：online：
	 * 	2）好友上线信息：online：
	 * 	3）好友下线信息：logout：
	 * 
	 * 3聊天信息  弹出聊天窗口 调用WinManage.receive
	 * 	1）群聊信息：to群聊：
	 * 	2）私聊信息：toXXX：
	 * 4系统公告！public：
	 * @throws Exception 
	 */
	public static void run() throws Exception{
		String message=null;
		Chatpanel cp = new Chatpanel();
		cp.start();
		String name=null;
		String []names=null;
		while((message=Link.br.readLine().trim())!=null){
			if("ls".equals(message)){
				break;
			}
			if("lwn".equals(message)){
				Warning w=new Warning("用户名错误");
			}
			if("lwp".equals(message)){
				Warning w=new Warning("密码错误");
			}
			if("ss".equals(message)){
				Warning w=new Warning("注册成功");
			}
			if("sf".equals(message)){
				Warning w=new Warning("注册失败");
			}
			if(message.startsWith("public")){
				message=message.substring(6);
				Warning w=new Warning(message);
			}
		}
		Thread.sleep(500);
		cp.run();		
		while((message=Link.br.readLine())!=null){
			//上线多人
			if(message.startsWith("os:")){
				message=message.substring(3);
				names=message.split(",");
				Chatpanel.removeall();
				for(String n:names){
					Chatpanel.addFri(n);
				}
			}
			//上线一人
			if(message.startsWith("oe:")){
				name=message.substring(3);
				Chatpanel.addFri(name);
			}
			//下线一人
			if(message.startsWith("lo:")){
				name=message.substring(3);
				Chatpanel.removeFri(name);
			}
			//聊天
			if(message.startsWith("to:")){
				name=message.substring(3,message.indexOf("@"));
				message=message.substring(name.length()+4);
				WinManage.receive(name, message);
			}
			if(message.startsWith("pl:")){
				message=message.substring(3);
				Warning w=new Warning(message);
			}
			
		}
	}

}
class Warning extends JFrame{
	public Warning(String str){
		super();
		Font f = new Font("黑体", 0, 20);
		Container c=getContentPane();
		JLabel jl=new JLabel(str);
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setFont(f);
		c.add(jl);
		setVisible(true);
		setBounds(700, 400, 200, 100);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
}
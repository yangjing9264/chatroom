package client;

import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MessageSift {
	/**
	 * �����¼��ִӷ�������������Ϣ
	 * 
	 * 1ϵͳ��Ϣ   ������
	 * 	1��ע��ɹ����ɹ���ʧ�� signin��succeed fail  
	 * 	2����½�ɹ����ɹ���ʧ�ܣ��û��������������login succeed wrongname wrongpwd
	 * 
	 * 2����״̬��Ϣ  ˢ�º����б�  ����Chatpanel��addFri��removeFri
	 * 	1����ǰ���ߺ��ѣ�online��
	 * 	2������������Ϣ��online��
	 * 	3������������Ϣ��logout��
	 * 
	 * 3������Ϣ  �������촰�� ����WinManage.receive
	 * 	1��Ⱥ����Ϣ��toȺ�ģ�
	 * 	2��˽����Ϣ��toXXX��
	 * 4ϵͳ���棡public��
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
				Warning w=new Warning("�û�������");
			}
			if("lwp".equals(message)){
				Warning w=new Warning("�������");
			}
			if("ss".equals(message)){
				Warning w=new Warning("ע��ɹ�");
			}
			if("sf".equals(message)){
				Warning w=new Warning("ע��ʧ��");
			}
			if(message.startsWith("public")){
				message=message.substring(6);
				Warning w=new Warning(message);
			}
		}
		Thread.sleep(500);
		cp.run();		
		while((message=Link.br.readLine())!=null){
			//���߶���
			if(message.startsWith("os:")){
				message=message.substring(3);
				names=message.split(",");
				Chatpanel.removeall();
				for(String n:names){
					Chatpanel.addFri(n);
				}
			}
			//����һ��
			if(message.startsWith("oe:")){
				name=message.substring(3);
				Chatpanel.addFri(name);
			}
			//����һ��
			if(message.startsWith("lo:")){
				name=message.substring(3);
				Chatpanel.removeFri(name);
			}
			//����
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
		Font f = new Font("����", 0, 20);
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
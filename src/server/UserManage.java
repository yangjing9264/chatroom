package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class UserManage {
	static private Map<String, PrintWriter> onlineMap = new HashMap<String, PrintWriter>();
	static private Map<String, String> regMap = new HashMap<String, String>();
	private String Uname;
	private String host;
	private PrintWriter pw;
	private BufferedReader br;
	
	public UserManage(String host,PrintWriter pw,BufferedReader br){
		this.host=host;
		this.pw=pw;
		this.br=br;
		
	}
	public void run() throws Exception{
		String message=null;
		String name="";
		String pwd="";
		while((message=br.readLine().trim())!=null){
			//注册
			name=message.substring(3,message.indexOf("@"));
			pwd=message.substring(message.indexOf("@")+1);
			if(message.startsWith("sg:")){
				if(!regMap.containsKey(name)&&!"请输入用户名".equals(name)&&pwd.length()>0){
					pw.println("ss");
					put(name, pwd);
				}else{
					pw.println("sf");
				}
			}
			
			//登陆
			if(message.startsWith("lg:")){
				if(regMap.containsKey(name)){
					if(pwd.equals(regMap.get(name))){
						pw.println("ls");
						Uname=name;
						break;
					}else{
						pw.println("lwp");
					}
				}else{
					pw.println("lwn");
				}
			}
		}
		
		
		try {
			sendall("oe:"+Uname);
			put(Uname, pw);
			os();
			
			while((message=br.readLine())!=null){
				if(message.startsWith("to:")){
					name=message.substring(3,message.indexOf("@"));
					message=message.substring(3+name.length()+1);
					
					if("群聊".equals(name)){
						message="to:群聊@"+Uname+"@"+message;
						sendall(message);
					}
					if(onlineMap.containsKey(name)){
						message="to:"+Uname+"@"+message;
						send(name, message);
					}
					
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			
			removeOne(Uname);
			sendall("lo:"+Uname);
		}
		
		
		
	}
	
	public static synchronized void put(String name,String pwd){
		regMap.put(name, pwd);
	}
	public static synchronized void put(String name,PrintWriter pw){
		onlineMap.put(name, pw);
	}
	public static synchronized void removeOne(String name){
		onlineMap.remove(name);
	}
	public static synchronized void send(String name,String message){
		onlineMap.get(name).println(message);
	}
	public static synchronized void sendall(String message){
		for(PrintWriter p:onlineMap.values()){
			p.println(message);
		}
	}
	public static void os(){
		String users=onlineMap.keySet().toString();
		users=users.substring(1, users.length()-1);
		sendall("os:"+users);
	}

	
}

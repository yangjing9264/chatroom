package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * 该类用于提供一个TCP连接，并提供pw和br；
 * @author TYXFDZ
 *
 */
public class Link{
	static private Socket socket;
	static PrintWriter pw=null;
	static BufferedReader br=null;
	
	static public void link(String host,Integer port) throws Exception{
		try{
			socket=new Socket(host, port);
			
			start();

		}catch(Exception e){
			System.out.println("连接服务器失败");
			Warning w=new Warning("连接服务器失败");
			throw e;
		}
		
	}
	
	static public void start() throws Exception{
		try {
			OutputStream out=socket.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(out,"UTF-8");

			pw=new PrintWriter(osw,true);
			
			InputStream in=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(in,"UTF-8");

			br=new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	static public String close(){
		try {
			if(pw!=null){
				pw.close();
			}
			if(br!=null){
				br.close();
			}
			return "正常关闭！";
		} catch (Exception e) {
			return "关闭失败";
		}
	}

}

package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * ���������ṩһ��TCP���ӣ����ṩpw��br��
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
			System.out.println("���ӷ�����ʧ��");
			Warning w=new Warning("���ӷ�����ʧ��");
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
			return "�����رգ�";
		} catch (Exception e) {
			return "�ر�ʧ��";
		}
	}

}

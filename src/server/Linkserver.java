package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Linkserver {
	
	private ServerSocket server;
	private ExecutorService threadPool;
	
	
	public Linkserver(int port) throws Exception{
		try {
			server=new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("服务端初始化失败！");
			throw e;
		}
//		threadPool = Executors.newFixedThreadPool(50);
		
	}
	public void start() throws Exception{
		try {
			while (true){
				System.out.println("等待连接");
				final Socket socket =server.accept();
				System.out.println("一个客户连接上了");
				ClientHandler handler=new ClientHandler(socket);
				
				Thread t= new Thread(handler);
				t.start();
				
			}
		} catch (Exception e) {
			System.out.println("服务端异常");
			throw e;
		}
	}
	

}
class ClientHandler implements Runnable{
	private Socket socket;
	private String host;
	public ClientHandler(Socket socket){
		this.socket=socket;
		InetAddress ipad = socket.getInetAddress();
		host = ipad.getHostAddress();
	}
	public void run(){
		try {
			
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
			PrintWriter pw = new PrintWriter(osw, true);
			
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			UserManage um=new UserManage(host, pw, br);
			um.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			if(socket!=null){
				
				try {
					
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

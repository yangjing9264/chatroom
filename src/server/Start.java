package server;

public class Start {
	public static void main(String[] args) throws Exception {
		Linkserver link=new Linkserver(8088);
		link.start();
	}
}

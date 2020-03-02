package client;

public class Start {
	public static void main(String[] args) throws Exception {
		
		Link.link("localhost",8088);
		MessageSift.run();
	}

}

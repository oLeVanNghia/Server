package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private int port;
	private ServerSocket serverSocket;
	private Socket insocket;
	public Server(int inport) {
		// TODO Auto-generated constructor stub
		this.port = inport;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started");
			while(true){
				
				insocket = serverSocket.accept();
				DataTransfer trans = new DataTransfer(insocket);
				
				//new transfer thread
				Thread thread = new Thread(trans);
				thread.start();
			    
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server(6543);
	}

}

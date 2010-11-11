package Server;

/**
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements Runnable {
	private String proto;
	private int port;
	private String servername = null;
	private Socket socket = null;
	private String data = null;
	private ArrayList<String[]> infors = null;
	/**
	 * element struct of infors: 
	 * name,age, gender,andress,email,twitter,facebook,song,name,longitude,latitude,comment,time
	 */
	BufferedReader reader = null;
	PrintWriter writer = null;
	
	public Client(int inport, String inservername,String request) {
		this.port = inport;
		this.servername = inservername;
		this.proto = request;
		try {
			socket = new Socket(servername, port);
			System.out.println("Initial a connection successful"+socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infors = new ArrayList<String[]>();
	}
	public Client(int inport, String inservername, String data,String request) {
		this.port = inport;
		this.servername = inservername;
		this.data = data;
		this.proto = request;
		try {
			socket = new Socket(servername, port);
			System.out.println("Initial a connection successful");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infors = new ArrayList<String[]>();
	}
	/**
	 * get infors
	 * @return
	 */
	public ArrayList<String[]> getInfors(){
		return infors;
	}
	/**
	 * check socket connection
	 * @return
	 */
	public boolean isConnectSuccess(){
		if(socket!=null)
			return true;
		return false;
	}
	/**
	 * @param data
	 */
	public void setData(String data){
		this.data = data;
	}
	/**
	 * prepare request
	 */
	public String prepareRequest(){
		return proto.toUpperCase()+":"+data;
	}
	/**
	 * post
	 * @throws IOException 
	 */
	public void postProcess() throws IOException{
		String postData = prepareRequest();
		writer.println(postData);
		String line = reader.readLine();
		System.out.println(line);
	}
	/**
	 * get
	 * @throws IOException 
	 */
	public void getProcess() throws IOException{
		String getData = prepareRequest();
		//send request
		writer.println(getData);
		String line;
		String[] infor;
		//read data from server
		while((line = reader.readLine()) != null){
			System.out.println(line);
			infor = line.split(",");
			infors.add(infor);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (socket != null) {
			InputStream input = null;
			OutputStream output = null;
			
			try {
				input = socket.getInputStream();
				output = socket.getOutputStream();
				reader = new BufferedReader(new InputStreamReader(input));
				writer = new PrintWriter(output, true);
				if(proto.equals("post")){
					postProcess();
				}
				else{
					getProcess();
				}
				reader.close();
				writer.close();
				input.close();
				output.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * test class
	 */
//
//	public static void main(String args[]) {
//		// post
//		System.out.println("post");
//		String data = "nghia,11,male,111,223,ssd,fff,for ever,12,13,nothing\n";
//		Client connect = new Client(6543, "127.0.0.1", "post");
//		connect.setData(data);
//		Thread thread = new Thread(connect);
//		thread.start();
//		// get
//		System.out.println("get");
//		data = "forever";
//		connect = new Client(6543, "127.0.0.1", "get");
//		connect.setData(data);
//		thread = new Thread(connect);
//		thread.start();
//	}
}


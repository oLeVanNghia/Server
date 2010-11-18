package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserInfo {
	private String name;
	private String age;
	private String gender;
	private String address;
	//private String email;
	private String twitter;
	//private String facebook;
	
	String directory = "/data/data/com.player/"+"/files/";
	String filename = "property.txt";
	
	public UserInfo(String[] infor) {
		// TODO Auto-generated constructor stub
		this.name = infor[0];
		this.age = infor[1];
		this.gender = infor[2];
		this.address = infor[3];
		this.twitter = infor[4];
		//this.email = infor[4];
		//this.facebook = infor[6];
	}
	public UserInfo(String name, String age, String gender, String address,String twitter) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.twitter = twitter;
		//this.facebook = facebook;
		//this.email = email;
	}
	public String getName(){
		return name;
	}
	public String toString(){
		return name+","+age+","+gender+","+address+","+","+twitter;
	}
	public void load() throws IOException{
		File file = new File(directory+filename);
		if(!file.exists()){
			file.createNewFile();
		}
		FileReader input = new FileReader(file);
		BufferedReader reader = new BufferedReader(input);
		String line;
		if((line = reader.readLine()) != null){
			String[] infor = line.split(",");
			this.name = infor[0];
			this.age = infor[1];
			this.gender = infor[2];
			this.address = infor[3];
			this.twitter = infor[4];
		}
		reader.close();
	}
	public void save(){
		File file = new File(directory+filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter output;
		try {
			output = new FileWriter(file);
			BufferedWriter writter = new BufferedWriter(output);
			writter.write(this.toString());
	
			writter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

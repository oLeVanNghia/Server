package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PropertyFile {
	private String directory;
	private String filename;
	private ArrayList<UserInfo> property = new ArrayList<UserInfo>();
	public PropertyFile() {
		// TODO Auto-generated constructor stub
		directory = "/data/data/com.player/"+"/files/";
		filename = "property.txt";
		
		try {
			loadProperty();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean isUserExists(UserInfo user){
		for (UserInfo info : property) {
			if(info.getName().equals(user.getName())){
				return true;
			}
		}
		return false;
	}
	/**
	 * add new user
	 * @param user
	 */
	public void addUser(UserInfo user){
		if(!isUserExists(user))
			this.property.add(user);
	}
	/**
	 * get property
	 * @return
	 */
	public ArrayList<UserInfo> getProperty(){
		return property;
	}
	/**
	 * get property name
	 */
	public ArrayList<String> getPropertyName(){
		ArrayList<String> pname = new ArrayList<String>();
		for (UserInfo user : property) {
			pname.add(user.getName());
		}
		return pname;
	}
	/**
	 * save property
	 */
	public void save(){
		try {
			saveProperty();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * write property to file
	 * @throws IOException
	 */
	public void saveProperty() throws IOException{
		File file = new File(directory+filename);
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter output = new FileWriter(file);
		BufferedWriter writter = new BufferedWriter(output);
		
		for (UserInfo user : property) {
			writter.write(user.toString());
			writter.write("\n");
		}
		writter.close();
	}
	/**
	 * load property
	 * @throws IOException
	 */
	public void loadProperty() throws IOException{
		File file = new File(directory+filename);
		if(!file.exists()){
			file.createNewFile();
		}
		FileReader input = new FileReader(file);
		BufferedReader reader = new BufferedReader(input);
		String line;
		while((line = reader.readLine()) != null){
			String[] infor = line.split(",");
			UserInfo user = new UserInfo(infor);
			this.property.add(user);
		}
		reader.close();
	}
}

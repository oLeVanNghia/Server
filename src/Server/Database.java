package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;

public class Database {
	private Connection conn = null;
	private ResultSet result = null;
	private PreparedStatement pstate = null;

	public Database() {
		// TODO Auto-generated constructor stub
	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/Research";
			conn = DriverManager.getConnection(url, "root", "");
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeConnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addQuery(String query) {
	}

	/**
	 * search user
	 * 
	 * @param username
	 * @return true if user exist and vice versa
	 */
	public boolean searchUser(String username) {
		// create connect
		this.connect();

		String query = "select * from user where name = ?";
		try {
			pstate = conn.prepareStatement(query);
			pstate.setString(1, username);
			result = pstate.executeQuery();

			// if user exist
			if (result.next()) {
				this.closeConnect();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnect();
		return false;
	}

	/**
	 * search song and user in music table
	 * 
	 * @param song
	 * @param user
	 * @return true if exist and vice versa
	 */
	public boolean searchSongAndUser(String song, String user) {
		this.connect();
		String query = "select * from music where song = ? and name = ?";

		try {
			pstate = conn.prepareStatement(query);
			pstate.setString(1, song);
			pstate.setString(2, user);

			result = pstate.executeQuery();

			if (result.next()) {
				result.close();
				pstate.close();
				this.closeConnect();
				return true;
			}
			result.close();
			pstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnect();
		return false;
	}

	/**
	 * insert new data into music table
	 * 
	 * @param song
	 * @param name
	 * @param longitute
	 * @param latitude
	 * @param comment
	 */
	public void insertMusic(String song, String name, int longitude,
			int latitude, String comment) {
		this.connect();

		String query = "insert into music values(?,?,?,?,?,?)";

		try {
			pstate = conn.prepareStatement(query);
			pstate.setString(1, song);
			pstate.setString(2, name);
			pstate.setInt(3, longitude);
			pstate.setInt(4, latitude);
			pstate.setString(5, comment);
			java.util.Date date = new java.util.Date();
			pstate.setDate(6, new java.sql.Date(date.getTime()));

			pstate.executeUpdate();
			pstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnect();
	}
	/**
	 * update music information
	 * @param song
	 * @param name
	 * @param longitude
	 * @param latitude
	 * @param comment
	 */
	public void updateMusic(String song, String name,int longitude, int latitude, String comment) {
		this.connect();

		String query = "update music set longtitude= ?, latitude=?,comment = ?, time = ? where song =? AND name=?";

		try {
			pstate = conn.prepareStatement(query);
			pstate.setInt(1, longitude);
			pstate.setInt(2, latitude);
			pstate.setString(3, comment);
			java.util.Date date = new java.util.Date();
			pstate.setDate(4, new java.sql.Date(date.getTime()));

			pstate.setString(5, song);
			pstate.setString(6, name);
			
			pstate.executeUpdate();
			pstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnect();
	}

	/**
	 * insert user into user table
	 * 
	 * @param infor
	 *            : user information
	 */
	public void insertUser(String[] infor) {
		this.connect();

		String query = "insert into user values(?,?,?,?,?,?,?)";

		try {
			pstate = conn.prepareStatement(query);
			pstate.setString(1, infor[0]);
			pstate.setInt(2, Integer.valueOf(infor[1]));
			pstate.setString(3, infor[2]);
			pstate.setString(4, infor[3]);
			pstate.setString(5, infor[4]);
			pstate.setString(6, infor[5]);
			pstate.setString(7, infor[6]);

			pstate.executeUpdate();
			pstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.closeConnect();
	}
	/**
	 * update user information
	 * @param infor
	 */
	public void updateUser(String[] infor) {
		this.connect();

		String query = "update user set age=?,gender=?,andress=?,email=?,twitter=?,facebook=? where name=?";

		try {
			pstate = conn.prepareStatement(query);
			
			pstate.setInt(1, Integer.valueOf(infor[1]));
			pstate.setString(2, infor[2]);
			pstate.setString(3, infor[3]);
			pstate.setString(4, infor[4]);
			pstate.setString(5, infor[5]);
			pstate.setString(6, infor[6]);
			
			pstate.setString(7, infor[0]);
			
			pstate.executeUpdate();
			pstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.closeConnect();
	}
	/**
	 * get user infor and song of user who is listening same the song
	 * @param song
	 * @return
	 */
	public ArrayList<String[]> getSameMusicListening(String song){
		ArrayList<String[]> rsList = new ArrayList<String[]>();
		
		this.connect();
		result = null;
		
		String query = "select * from user inner join music on user.name = music.name where song = ?";
		
		try {
			pstate = conn.prepareStatement(query);
			pstate.setString(1, song);
			//java.util.Date datemin = new java.util.Date();
			//datemin.setHours(datemin.getHours()-1);
			//pstate.setDate(2, new java.sql.Date(datemin.getTime()));
			
			result = pstate.executeQuery();
			
			try {
				ResultSetMetaData rsMt = (ResultSetMetaData) result.getMetaData();
				int colume = rsMt.getColumnCount();
				while(result.next()){
					String[] infor = new String[colume];
					for(int i = 1; i<= colume; i++){
						infor[i-1] = result.getString(i);
					}
					rsList.add(infor);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.close();
			pstate.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnect();
		return rsList;
	}
//	public static void main(String args[]){
//		Database db = new Database();
//		
//		db.updateMusic("forever", "nghia", 1234, 1234, "test");
//	}
}

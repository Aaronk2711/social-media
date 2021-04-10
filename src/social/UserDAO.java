package social;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.jdbc.Blob;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class UserDAO {
	
	private final String url;
	private final String username;
	private final String password;
	public String pfp64;
	
	public UserDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public User getUser(int user_id) throws SQLException {
		final String sql = "SELECT * FROM users WHERE user_id = ?";
		
		User user = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  user_id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String college = rs.getString("college");
			String school = rs.getString("school");
			String hometown = rs.getString("hometown");
			LocalDate birthday = rs.getDate("birthday").toLocalDate();
			String email = rs.getString("email");
			Long phone = rs.getLong("phone");
			Instant lastMod = rs.getTimestamp("lastmod").toInstant();
			Instant lastLogin = rs.getTimestamp("lastlogin").toInstant();
			String password = rs.getString("password");
			java.sql.Blob encPic = rs.getBlob("encpic");
			byte[] profilePic = encPic.getBytes(1,  (int) encPic.length());
			
			user = new User(user_id, firstName, lastName, college, school, hometown, birthday, email, phone, lastMod, lastLogin, password, profilePic);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return user;
	}
	
	public List<User> getUsers() throws SQLException {
		final String sql = "SELECT * FROM users ORDER BY user_id ASC";
		
		List<User> users = new ArrayList<User>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int user_id = rs.getInt("user_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String college = rs.getString("college");
			String school = rs.getString("school");
			String hometown = rs.getString("hometown");
			LocalDate birthday = rs.getDate("birthday").toLocalDate();
			String email = rs.getString("email");
			Long phone = rs.getLong("phone");
			Instant lastMod = rs.getTimestamp("lastmod").toInstant();
			Instant lastLogin = rs.getTimestamp("lastlogin").toInstant();
			String password = rs.getString("password");
			java.sql.Blob encPic = rs.getBlob("encpic");
			byte[] profilePic = encPic.getBytes(1,  (int) encPic.length());
			
			
			
			
			users.add(new User(user_id, firstName, lastName, college, school, hometown, birthday, email, phone, lastMod, lastLogin, password, profilePic));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return users;
	}
	
	public List<User> getOthers(int userID) throws SQLException, IOException {
		final String sql = "SELECT * FROM users WHERE user_id NOT IN (" + userID + ")";
		
		List<User> users = new ArrayList<User>();
		Connection conn = getConnection(); 
		PreparedStatement pstmt = conn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int user_id = rs.getInt("user_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String college = rs.getString("college");
			String school = rs.getString("school");
			String hometown = rs.getString("hometown");
			LocalDate birthday = rs.getDate("birthday").toLocalDate();
			String email = rs.getString("email");
			Long phone = rs.getLong("phone");
			Instant lastMod = rs.getTimestamp("lastmod").toInstant();
			Instant lastLogin = rs.getTimestamp("lastlogin").toInstant();
			String password = rs.getString("password");
			java.sql.Blob encPic = rs.getBlob("encpic");
			byte[] profilePic = encPic.getBytes(1,  (int) encPic.length());
			
			users.add(new User(user_id, firstName, lastName, college, school, hometown, birthday, email, phone, lastMod, lastLogin, password, profilePic));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return users;
	}
	
	public User login(String email, String password) throws SQLException, IOException {
		final String sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
		
		User user = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery(sql);
		
		if (rs.next()) {
			int user_id = rs.getInt("user_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String college = rs.getString("college");
			String school = rs.getString("school");
			String hometown = rs.getString("hometown");
			LocalDate birthday = rs.getDate("birthday").toLocalDate();
			String email_address = rs.getString("email");
			Long phone = rs.getLong("phone");
			Instant lastMod = rs.getTimestamp("lastmod").toInstant();
			Instant lastLogin = rs.getTimestamp("lastlogin").toInstant();
			String passphrase = rs.getString("password");
			java.sql.Blob encPic = rs.getBlob("encpic");
			byte[] profilePic = encPic.getBytes(1,  (int) encPic.length());
			
			user = new User(user_id, firstName, lastName, college, school, hometown, birthday, email_address, phone, lastMod, lastLogin, passphrase, profilePic);
			
			updateLoginTime(user);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		
		return user;
		
	}
	
	public boolean updateLoginTime(User user) throws SQLException {
		final String sql = "UPDATE users SET lastlogin = ? " + "WHERE user_id = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setTimestamp(1,  Timestamp.from(Instant.now()));
		pstmt.setInt(2,  user.getId());
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	public boolean updateUser(User user) throws SQLException {
		final String sql = "UPDATE users SET first_name = ?, last_name = ?, college = ?, school = ?, hometown = ?, birthday = ?, email = ?, phone = ?, lastmod = ?, lastlogin = ?, password = ?, encpic = ? " +
	    		"WHERE user_id = ?"; 
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,  user.getFirstName());
		pstmt.setString(2,  user.getLastName());
		pstmt.setString(3,  user.getCollege());
		pstmt.setString(4,  user.getSchool());
		pstmt.setString(5,  user.getHometown());
		pstmt.setDate(6,  java.sql.Date.valueOf(user.getBirthday()));
		pstmt.setString(7,  user.getEmail());
		pstmt.setLong(8,  user.getPhone());
		pstmt.setTimestamp(9,  Timestamp.from(user.getLastMod())); //lastMod
		pstmt.setTimestamp(10,  Timestamp.from(user.getLastLogin())); //lastLogin
		pstmt.setString(11,  user.getPassword());
		pstmt.setBlob(12,  new SerialBlob(user.getProfilePic()));
		pstmt.setInt(13,  user.getId());
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		
		return affected == 1;
		
	}
	
	public boolean deleteUser(User user) throws SQLException {
		final String sql = "DELETE FROM users WHERE user_id = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  user.getId());
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}

}
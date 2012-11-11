package nanoFuntas.qqsngServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
	Connection conn = null;
	final String url = "jdbc:mysql://localhost:3306/QQSNGDB";
	final String user = "QQSNGAdmin";
	final String password = "qqsngserver1110";
	boolean DEBUG = true;
	final String TAG = "DataBaseService";
	
	private Connection getDBConnection(){
		if(DEBUG) System.out.println(TAG + ": getDBConnection");
		
		if(conn == null){
			if(DEBUG) System.out.println(TAG + ": conn == null");
			
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{
			if(DEBUG) System.out.println(TAG + ": getDBConnection, conn != null");
			// added 
			
			try {
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return conn;
	}

	public boolean isUserRegistered(String selfID){
		if(DEBUG) System.out.println(TAG + ": isUserRegistered");

		Statement st = null;
		ResultSet rs = null;
		boolean result = false;
		
		conn = getDBConnection();

		try{
			st = conn.createStatement();
			rs = st.executeQuery("select * from USER_INFO");
			
			while(rs.next()){
				if( rs.getString("UID").equals(selfID) ){
					result = true;
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) { conn.close(); if(DEBUG) System.out.println(TAG + ": isUserRegistered, conn !=null"); }
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int registerUser(String selfID){
		if(DEBUG) System.out.println(TAG + ": registerUser(selfID)");
		PreparedStatement ps = null;
		conn = getDBConnection();
		int result = 1;
		
		try{
			ps = conn.prepareStatement("INSERT INTO USER_INFO VALUES(?, ?, ?, ?)");
			ps.setString(1, selfID);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
/*	
	public boolean isUserIdentified(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": isUserIdentified");

		conn = getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		
		boolean result = false;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from USER_LOGIN_INFO");
			
			while(rs.next()){
				if(rs.getString("EMAIL").equals(email) && rs.getString("PASSWORD").equals(pw))
					result = true;					
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
				if(rs != null){
					rs.close();
				}	
				if(st != null){
					st.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		return result;
	}
	
	public int registerUser(String email, String pw){
		if(DEBUG) System.out.println(TAG + ": registerUser");
		
		conn = getDBConnection();
		int result = 0;

		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO USER_LOGIN_INFO VALUES(?, ?)");
			ps.setString(1, email);
			ps.setString(2, pw);
			ps.executeUpdate();
						
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;		
	}
*/
}

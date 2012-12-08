package nanoFuntas.qqsngServer.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nanoFuntas.qqsngServer.Util;

public class DBHelper {
	static boolean DEBUG = true;
	static final String TAG = "DataBaseService";

	static final String url = "jdbc:mysql://localhost:3306/QQSNGDB";
	static final String user = "QQSNGAdmin";
	static final String password = "qqsngserver1110";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Connection getDBConnection() {
		if (DEBUG)
			System.out.println(TAG + ": getDBConnection()");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 
	 * @param selfID
	 * @return null if no user info found, UserInfo instance if info found.
	 */
	public static UserInfo[] getFriendsList(String selfID) {
		// TODO:this is incomplete...
		UserInfo[] uis = null;

		uis = new UserInfo[5];
		for (UserInfo u : uis) {
			u.gold = u.heart = u.score = 999;
			u.id = "dummy return value";
		}

		return uis;
		/*
		 * TODO: fill thsi loginc PreparedStatement st = null; ResultSet rs =
		 * null; Connection conn = getDBConnection();
		 * 
		 * 
		 * 
		 * 
		 * try { String sql = "select * from friends where UDI = ?"; st =
		 * conn.prepareStatement(sql); st.setString(1, selfID); rs =
		 * st.executeQuery(); if (rs.first()) { } } catch (Exception e) {
		 * e.printStackTrace(); } finally { Util.safeClose(st,rs,conn); } return
		 * null;
		 */
	}

	/**
	 * 
	 * @param selfID
	 * @return null if no user info found, UserInfo instance if info found.
	 */
	public static UserInfo getSelfInfo(String selfID) {
		UserInfo ui = null;

		PreparedStatement st = null;
		ResultSet rs = null;
		Connection conn = getDBConnection();

		try {
			String sql = "select * from USER_INFO where UDI = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, selfID);
			rs = st.executeQuery();
			if (rs.first()) {
				ui = new UserInfo();
				ui.id = selfID;
				ui.gold = rs.getInt("GOLD");
				ui.heart = rs.getInt("HEART");
				ui.score = rs.getInt("SCORE");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.safeClose(conn, rs, st);
		}
		return ui;
	}

	/**
	 * 
	 * @param selfID
	 * @return true if user already exists, otherwise false.
	 */
	public static boolean isUserRegistered(String selfID) {
		if (DEBUG)
			System.out.println(TAG + ": isUserRegistered()");

		PreparedStatement st = null;
		ResultSet rs = null;
		boolean result = false;

		Connection conn = getDBConnection();

		try {
			String sql="select * from USER_INFO where UID equals ?";
			st = conn.prepareStatement(sql);
			st.setString(1, selfID);
			rs = st.executeQuery();

			while (rs.next()) {
				if (rs.getString("UID").equals(selfID)) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.safeClose(conn, rs, st);
		}

		return result;
	}

	/**
	 * 
	 * @param selfID
	 * @return true on success, false on fail.
	 */
	public static  boolean registerUser(String selfID) {
		if (DEBUG)
			System.out.println(TAG + ": registerUser()");
		PreparedStatement ps = null;
		Connection conn = getDBConnection();
		boolean result = false;

		try {
			ps = conn
					.prepareStatement("INSERT INTO USER_INFO VALUES(?, ?, ?, ?)");
			ps.setString(1, selfID);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			int ret = ps.executeUpdate();
			result = ret == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.safeClose(conn, ps);
		}

		return result;
	}

	/*
	 * public boolean isUserIdentified(String email, String pw){ if(DEBUG)
	 * System.out.println(TAG + ": isUserIdentified");
	 * 
	 * conn = getDBConnection(); Statement st = null; ResultSet rs = null;
	 * 
	 * boolean result = false; try { st = conn.createStatement(); rs =
	 * st.executeQuery("select * from USER_LOGIN_INFO");
	 * 
	 * while(rs.next()){ if(rs.getString("EMAIL").equals(email) &&
	 * rs.getString("PASSWORD").equals(pw)) result = true; }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { try{ if(rs != null){ rs.close(); } if(st
	 * != null){ st.close(); } if(conn != null){ conn.close(); } }
	 * catch(Exception e){ e.printStackTrace(); } } return result; }
	 * 
	 * public int registerUser(String email, String pw){ if(DEBUG)
	 * System.out.println(TAG + ": registerUser");
	 * 
	 * conn = getDBConnection(); int result = 0;
	 * 
	 * try { PreparedStatement ps =
	 * conn.prepareStatement("INSERT INTO USER_LOGIN_INFO VALUES(?, ?)");
	 * ps.setString(1, email); ps.setString(2, pw); ps.executeUpdate();
	 * 
	 * System.out.println(result); } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return result; }
	 */
}

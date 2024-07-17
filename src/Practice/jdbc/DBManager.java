package Practice.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Connection 객체를 생성, 전달
//ResultSet ,  객체 종료 close()
public class DBManager {
	static String url = "Practice.jdbc:mysql://localhost:3306/madangdb";
	static String user = "root";
	static String password = "GEU1224kk!";

	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = java.sql.DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void releaseConnection(PreparedStatement pstmt,Connection conn) {
		try {
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void releaseConnection(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

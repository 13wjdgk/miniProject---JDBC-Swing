package Practice.jdbc;

import static Practice.jdbc.DBManager.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test2 {



	public static void main(String[] args)  {
		//insertCustomer();
		//updateCustomer();
		//selectList();
		//selectDetail();
		deleteCustomer();

		//delete
		// String selectSql = "delete from customer where custid = 262";
		// int rs = stmt.executeUpdate(selectSql);
		// System.out.println(rs);
		//
		// stmt.close();
		// conn.close();

	}
	static void insertCustomer() {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			//insert
			String sql = "insert into customer values(261,'손흥민','영국 토트넘','010-0000-0000');";
			System.out.println(stmt.executeUpdate(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	static void updateCustomer() {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			//insert
			String updateSql = "update customer set address = '한국 서울' where custid = 261";
			int ret = stmt.executeUpdate(updateSql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	static void selectList() {
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String selectSql = "select * from customer ";
			rs = stmt.executeQuery(selectSql);
			while(rs.next()){
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	static void selectDetail() {
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String selectSql = "select * from customer where custid = 261";
			rs = stmt.executeQuery(selectSql);
			while(rs.next()){
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	static void deleteCustomer() {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			//insert
			String selectSql = "delete from customer where custid = 261";
			int rs = stmt.executeUpdate(selectSql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
}

package Practice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) throws SQLException {
		String url = "Practice.jdbc:mysql://localhost:3306/madangdb";
		String user = "root";
		String password = "GEU1224kk!";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();


		//insert
		//String sql = "insert into customer values(261,'손흥민','영국 토트넘','010-0000-0000');";
		// System.out.println(stmt.executeUpdate(sql));

		//update
		//String updateSql = "update customer set address = '한국 서울' where custid = 261";
		//int ret = stmt.executeUpdate(updateSql);

		// //select
		// String selectSql = "select * from customer where custid = 261";
		// ResultSet rs = stmt.executeQuery(selectSql);
		// if(rs.next()){
		// 	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
		// }

		//delete
		String selectSql = "delete from customer where custid = 262";
		int rs = stmt.executeUpdate(selectSql);
		System.out.println(rs);

		stmt.close();
		conn.close();

	}
}

package MiniProject.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import MiniProject.DBMannager;
public class TransactionTest {

	public static void main(String[] args){
		Connection con = null;
		PreparedStatement pstmt = null;

		String insertSql = "insert into customer values (?, ?, ?, ?); "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;

		try {

			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(insertSql);
			con.setAutoCommit(false);
			pstmt.setInt(1, 1);
			pstmt.setString(2, "홍길동");
			pstmt.setString(3, "서울시 강남구");
			pstmt.setString(4, "010-1234-5678");

			ret = pstmt.executeUpdate();

			pstmt.setInt(1, 2);
			pstmt.setString(2, "홍길동");
			pstmt.setString(3, "서울시 강남구");
			pstmt.setString(4, "010-1234-5678");
			ret = pstmt.executeUpdate();

			pstmt.setInt(1, 3);
			pstmt.setString(2, "홍길동");
			pstmt.setString(3, "서울시 강남구");
			pstmt.setString(4, "010-1234-5678");
			ret = pstmt.executeUpdate();

			pstmt.setInt(1, 4);
			pstmt.setString(2, "홍길동");
			pstmt.setString(3, "서울시 강남구");
			pstmt.setString(4, "010-1234-5678");
			ret = pstmt.executeUpdate();

			con.commit();

		}catch(SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBMannager.releaseConnection( pstmt, con);
		}

	}


}







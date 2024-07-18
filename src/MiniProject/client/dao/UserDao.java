package MiniProject.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import MiniProject.client.dto.User;
import MiniProject.DBMannager;
import MiniProject.client.enums.GradeEnum;
import MiniProject.client.enums.RoleEnum;
import MiniProject.client.enums.StatusEnum;


public class UserDao {

	public User findUserByEmail(String email) {
		User user = null;
		String sql = "select * from users where email = ?; ";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				user = new User();
				user.setEmail(rs.getString("email"));
				user.setUserId(Integer.parseInt(rs.getString("user_id")));
				user.setRole(RoleEnum.valueOf(rs.getString("role")));
				user.setGrade(GradeEnum.valueOf(rs.getString("grade")));
				user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
				user.setPhone(rs.getString("phone"));
				user.setCurrentAddress(rs.getString("current_address"));
				user.setStatus(StatusEnum.valueOf(rs.getString("status")));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return user;
	}

}

package MiniProject.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import MiniProject.DBMannager;
import MiniProject.client.dto.FavoritesDTO;


public class FavoritesDao {

	public FavoritesDao() {
	}

	public void setFavorite(int userId, int storeId, boolean isFavorite) {
		if(isFavorite) {
			System.out.println("deleteFavorite");
			deleteFavorite(userId, storeId);
		}else {
			addFavorite(userId, storeId);
		}
	}
	public void addFavorite(int userId, int storeId) {
		String sql = "insert into favorites(user_id, store_id,favorited_at) values(?, ?,?);";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, storeId);
			pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection( pstmt, con);
		}
	}
	public void deleteFavorite(int userId, int storeId) {
		String sql = "delete from favorites where user_id = ? AND store_id = ?;";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, storeId);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection( pstmt, con);
		}
	}
	public boolean findLikeByUserIdAndStoreId(int userId, int storeId){

		String sql = "select * from favorites where user_id = ? AND store_id = ?; ";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FavoritesDTO favorites = null;
		Boolean result = false;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, storeId);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				favorites = new FavoritesDTO();
				if(rs != null) {
					if(rs.getInt("user_id") == userId && rs.getInt("store_id") == storeId) {
						result = true;
					}
				}
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return result;
	}
}

package MiniProject.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MiniProject.DBMannager;
import MiniProject.client.dto.StoreDTO;


public class StoreDao {
	public List<StoreDTO> findAll() {

		String sql = "select * from store ; ";
		List<StoreDTO> stores = new ArrayList<StoreDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				StoreDTO store = new StoreDTO();
				store.setName(rs.getString("name"));
				store.setAddress(rs.getString("address"));
				store.setStoreId(rs.getInt("store_id"));
				store.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				store.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
				stores.add(store);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return stores;
	}


	public StoreDTO findStoreById(int storeId) {

		String sql = "select * from store where store_id = ?; ";
		List<StoreDTO> stores = new ArrayList<StoreDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoreDTO store = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, storeId);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				store = new StoreDTO();
				store.setName(rs.getString("name"));
				store.setAddress(rs.getString("address"));
				store.setStoreId(rs.getInt("store_id"));
				store.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				store.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
				stores.add(store);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return store;
	}
}

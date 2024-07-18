package MiniProject.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MiniProject.DBMannager;
import MiniProject.client.dto.MenuDTO;


public class MenuDao {

	public MenuDao() {
	}

	public List<MenuDTO> findMenuListByStoreId( int storeId) {
		//Menu 테이블과 , MenuCommonCode 조인
		String sql = "select menu_id,store_id,M.name,price,  C.name as category,spice,amount from menu M join MenuCommonCode C on M.category=C.codeId   where store_id = ?; ";
		List<MenuDTO> menus = new ArrayList<MenuDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, storeId);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				MenuDTO menu = new MenuDTO();
				menu.setName(rs.getString("name"));
				menu.setPrice(rs.getBigDecimal("price"));
				menu.setStoreId(rs.getInt("store_id"));
				menu.setCategory(rs.getString("category"));
				menu.setSpice(rs.getString("spice"));
				menu.setAmount(rs.getString("amount"));
				menus.add(menu);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return menus;


	}
	public MenuDTO findMenuByStoreIdAndName( int storeId , String name) {
		//Menu 테이블과 , MenuCommonCode 조인
		String sql = "select menu_id,store_id,M.name,price,  C.name as category,spice,amount from menu M join MenuCommonCode C on M.category=C.codeId   where store_id = ? and M.name = ?; ";
		MenuDTO menu = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, storeId);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				menu = new MenuDTO();
				menu.setMenuId(rs.getInt("menu_id"));
				menu.setName(rs.getString("name"));
				menu.setPrice(rs.getBigDecimal("price"));
				menu.setStoreId(rs.getInt("store_id"));
				menu.setCategory(rs.getString("category"));
				menu.setSpice(rs.getString("spice"));
				menu.setAmount(rs.getString("amount"));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return menu;


	}

}

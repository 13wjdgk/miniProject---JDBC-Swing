package MiniProject.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MiniProject.DBMannager;
import MiniProject.client.dto.MenuCommonCodeDTO;
import MiniProject.client.dto.MenuDTO;

public class MenuCommonCodeDao {
	public MenuCommonCodeDao() {
	}
	public List<MenuCommonCodeDTO> findCommonCodeByCodeType( String codeType) {
		//Menu 테이블과 , MenuCommonCode 조인
		String sql = "select * from  MenuCommonCode where codeTypeID = ?; ";
		List<MenuCommonCodeDTO> commonCodes = new ArrayList<MenuCommonCodeDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, codeType);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				MenuCommonCodeDTO commonCodeDTO = new MenuCommonCodeDTO();
				commonCodeDTO.setCodeId(rs.getInt("codeId"));
				commonCodeDTO.setCodeTypeID(rs.getString("codeTypeID"));
				commonCodeDTO.setName(rs.getString("name"));
				commonCodes.add(commonCodeDTO);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return commonCodes;


	}

}

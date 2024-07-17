package Practice.jdbc;

import static Practice.jdbc.DBManager.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test4 {


	public static void main(String[] args)  {
		CustomerDto dto = new CustomerDto(263,"손흥민","영국 토트넘","010-0000-0000");
		int ret = updateCustomer(dto);
		System.out.println(ret);

	}
	static int insertCustomer(int custid,String name,String address,String phone) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int ret = 1;
		try {
			conn = DBManager.getConnection();
			String insertSql = "insert into customer values(?,?,?,?);";
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setInt(1, custid);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, phone);

			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(pstmt, conn);

		}
		return ret ;

	}

	static int updateCustomer(CustomerDto dto) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int ret = 1;
		try {
			conn = DBManager.getConnection();
			String updateSql = "update customer set address = ? where custid = ?";
			pstmt = conn.prepareStatement(updateSql);
			pstmt.setInt(2, dto.getCustId());
			pstmt.setString(1, dto.getAddress());

			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(pstmt, conn);

		}
		return ret ;

	}


	static List<CustomerDto> listCustomer() {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<CustomerDto> list = new ArrayList<>();
		try {
			conn = DBManager.getConnection();
			String selectSql = "select * from customer";
			pstmt = conn.prepareStatement(selectSql);

			rs = pstmt.executeQuery();

			while(rs.next()){
				CustomerDto dto = new CustomerDto();
				dto.setCustId(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(rs,pstmt, conn);

		}
		return list ;

	}

	static CustomerDto detaileCustomer(int custid) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CustomerDto dto = new CustomerDto();
		try {
			conn = DBManager.getConnection();
			String selectSql = "select * from customer where custid = ?";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, custid);

			rs = pstmt.executeQuery();

			if(rs.next()){
				dto.setCustId(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(rs,pstmt, conn);
		}
		return dto ;

	}

	static int deleteCustomer(int custId) {


		String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;
		// try with authoClose
		try(Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(deleteSql);){

			pstmt.setInt(1, custId);
			ret = pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}

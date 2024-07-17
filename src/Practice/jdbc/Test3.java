package Practice.jdbc;

import static Practice.jdbc.DBManager.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test3 {


	public static void main(String[] args)  {
		//insertCustomer(263,"손흥민","영국 토트넘","010-0000-0000");
		//updateCustomer(263,"한국 서울");
		List<CustomerDto> list = listCustomer();
		for(CustomerDto dto : list) {
			System.out.println(dto.getCustId() + " " + dto.getName() + " " + dto.getAddress() + " " + dto.getPhone());
		}
		//delete
		// String selectSql = "delete from customer where custid = 262";
		// int rs = stmt.executeUpdate(selectSql);
		// System.out.println(rs);
		//
		// stmt.close();
		// conn.close();

	}
	static int insertCustomer(int custid,String name,String address,String phone) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int ret = 1;
		try {
			conn = DriverManager.getConnection(url, user, password);
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
			try {
				pstmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return ret ;

	}

	static int updateCustomer(int custid,String address) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int ret = 1;
		try {
			conn = DriverManager.getConnection(url, user, password);
			String updateSql = "update customer set address = ? where custid = ?";
			pstmt = conn.prepareStatement(updateSql);
			pstmt.setInt(2, custid);
			pstmt.setString(1, address);

			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return ret ;

	}


	static List<CustomerDto> listCustomer() {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<CustomerDto> list = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url, user, password);
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
			try {
				pstmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list ;

	}

	static CustomerDto detaileCustomer(int custid) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CustomerDto dto = new CustomerDto();
		try {
			conn = DriverManager.getConnection(url, user, password);
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
			try {
				pstmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return dto ;

	}

	static int deleteCustomer(int custId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;

		try {
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(deleteSql);
			pstmt.setInt(1, custId);
			ret = pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

}

package MiniProject.client.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import MiniProject.DBMannager;
import MiniProject.client.dto.CartItem;

public class OrderDao {
	public OrderDao() {
	}
	public int insertOrder(int userId, int storeId, Integer totalPrice,LocalDateTime saveTime, List<CartItem> cartItems) {
		String insertOrderSql = "insert into orders (user_id, store_id, order_date, total_price) values (?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int result = 0;
		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, storeId);
			pstmt.setTimestamp(3, Timestamp.valueOf(saveTime));
			pstmt.setBigDecimal(4, BigDecimal.valueOf(totalPrice));
			int ret = pstmt.executeUpdate();

			System.out.println("ret : " + ret);
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}


		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection( pstmt, con);
		}
		return result;
	}

	public boolean insertOderItems(int orderId , int userId, int storeId, Integer totalPrice,LocalDateTime saveTime, List<CartItem> cartItems){
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		ResultSet rs = null;
		try {
			con = DBMannager.getConnection();
			con.setAutoCommit(false);
			String insertOrderItemSql = "INSERT INTO OrderItems (order_id, menu_id, quantity, price, spice, amount) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(insertOrderItemSql);

			for (CartItem cartItem : cartItems) {
				System.out.println(cartItem.toString());
				BigDecimal price = BigDecimal.valueOf(cartItem.getPrice().intValue()*cartItem.getQuantity());
				pstmt.setInt(1, orderId);
				pstmt.setInt(2, cartItem.getMenuId());
				pstmt.setInt(3, cartItem.getQuantity());
				pstmt.setBigDecimal(4, price);
				pstmt.setInt(5, cartItem.getSpice()==null?0:cartItem.getSpice());
				pstmt.setInt(6, cartItem.getAmount()==null?0:cartItem.getAmount());
				pstmt.addBatch(); // Add to batch
			}

			int[] resultList = pstmt.executeBatch(); // Execute the batch
			// Commit the transaction
			if(resultList.length == cartItems.size()){
				result = true;
				con.commit();
			}else{
				con.rollback();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection( pstmt, con);
		}

		return result;
	}
}

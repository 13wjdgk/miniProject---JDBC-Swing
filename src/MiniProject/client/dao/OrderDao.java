package MiniProject.client.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import MiniProject.DBMannager;
import MiniProject.client.dto.CartItem;
import MiniProject.client.dto.CarItem;

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
				System.out.println("dao--cartItem.getAmount() : " + cartItem.getAmount());
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

	public List<CarItem> findOrderHistoryById(int userId) {

		String sql = "SELECT o.order_id, o.store_id, o.user_id, o.order_date, o.total_price, o.state, s.name AS store_name, u.current_address " +
			"FROM Orders o " +
			"JOIN Users u ON o.user_id = u.user_id " +
			"JOIN Store s ON o.store_id = s.store_id " +
			"WHERE o.user_id = ? "+
			"ORDER BY o.order_date DESC";


		List<CarItem> orders = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarItem order = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				order = new CarItem();
				order.setOrderId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("user_id"));
				order.setStoreId(rs.getInt("store_id"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setTotalPrice(rs.getBigDecimal("total_price").intValue());
				order.setState(rs.getString("state"));
				order.setStoreName(rs.getString("store_name"));
				order.setDeliveryAddress(rs.getString("current_address"));
				orders.add(order);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return orders;
	}
	public List<CartItem> findOrderMenusHistoryById(int orderId) {
		System.out.println("orderId : " + orderId);
		String sql = "SELECT oi.order_id, oi.menu_id, oi.quantity, oi.price,mcc_spice.name AS spice_name, mcc_amount.name AS amount_name ,m.name as name "
			+ "FROM OrderItems oi "
			+ "LEFT JOIN  Menu m ON oi.menu_id = m.menu_id "
			+ "LEFT JOIN MenuCommonCode mcc_spice ON oi.spice = mcc_spice.codeId "
			+ "LEFT JOIN MenuCommonCode mcc_amount ON oi.amount = mcc_amount.codeId "
			+ "WHERE oi.order_id = ? ;";

		List<CartItem> orderMenus = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CartItem menu = null;

		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				menu = new CartItem();
				menu.setMenuId(rs.getInt("menu_id"));
				menu.setQuantity(rs.getInt("quantity"));
				menu.setPrice(rs.getBigDecimal("price"));
				menu.setSpiceName(rs.getString("spice_name"));
				menu.setAmountName(rs.getString("amount_name"));
				menu.setMenuName(rs.getString("name"));
				orderMenus.add(menu);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection(rs, pstmt, con);
		}
		return orderMenus;

	}
	public boolean cancelOrder(int orderId) {
		String sql = "UPDATE Orders SET state = ? WHERE order_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			con = DBMannager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "주문취소");
			pstmt.setInt(2, orderId);
			int ret = pstmt.executeUpdate();
			if (ret > 0) {
				result = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBMannager.releaseConnection( pstmt, con);
		}
		return result;
	}
}

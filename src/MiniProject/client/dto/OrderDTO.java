package MiniProject.client.dto;

import java.util.Date;

public class OrderDTO {
	private int orderId;
	private int storeId;
	private int userId;
	private Date orderDate;
	private double totalPrice;
	private String state;

	// 기본 생성자
	public OrderDTO() {
	}

	// 파라미터가 있는 생성자
	public OrderDTO(int orderId, int storeId, int userId, Date orderDate, double totalPrice, String state) {
		this.orderId = orderId;
		this.storeId = storeId;
		this.userId = userId;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.state = state;
	}

	// Getter 및 Setter 메서드
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getState() {
		return state;
	}
}


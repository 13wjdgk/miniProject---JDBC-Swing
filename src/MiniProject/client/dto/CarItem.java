package MiniProject.client.dto;

import java.util.Date;

public class CarItem {
	private int orderId;
	private int storeId;
	private int userId;
	private Date orderDate;
	private double totalPrice;
	private String state;
	private String storeName;

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

	public void setState(String state) {
		this.state = state;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	private String deliveryAddress;


	// 기본 생성자
	public CarItem() {
	}
	public CarItem(int orderId, int storeId, int userId, Date orderDate, double totalPrice, String state, String storeName, String deliveryAddress) {
		this.orderId = orderId;
		this.storeId = storeId;
		this.userId = userId;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.state = state;
		this.storeName = storeName;
		this.deliveryAddress = deliveryAddress;
	}
	public String toString() {
		return "CarItem [orderId=" + orderId + ", storeId=" + storeId + ", userId=" + userId + ", orderDate="
				+ orderDate + ", totalPrice=" + totalPrice + ", state=" + state + ", storeName=" + storeName
				+ ", deliveryAddress=" + deliveryAddress + "]";
	}
}

package MiniProject.client.dto;
public class OrderItemDTO {
	private int orderId;
	private int menuId;
	private int quantity;
	private Integer price;
	private Integer spice;
	private String amountName;
	private String spiceName;
	private Integer amount;

	// 생성자
	public OrderItemDTO(int orderId, int menuId, int quantity, Integer price, String amountName,Integer spice,String spiceName, Integer amount) {
		this.orderId = orderId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.price = price;
		this.spice = spice;
		this.amount = amount;
		this.amountName = amountName;
		this.spiceName = spiceName;
	}

	// getter 및 setter 메서드
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSpice() {
		return spice;
	}

	public void setSpice(Integer spice) {
		this.spice = spice;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getSpiceName() {
		return spiceName;
	}

	public void setSpiceName(String spiceName) {
		this.spiceName = spiceName;
	}

	public String getAmountName() {
		return amountName;
	}

	public void setAmountName(String amountName) {
		this.amountName = amountName;
	}


	@Override
	public String toString() {
		return "OrderItemDTO{" +
			"orderId=" + orderId +
			", menuId=" + menuId +
			", quantity=" + quantity +
			", price=" + price +
			", spice=" + spice +
			", amount=" + amount +
			'}';
	}
}

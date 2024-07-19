package MiniProject.client.dto;

import java.math.BigDecimal;

public class CartItem {
	private int menuId;
	private String menuName;
	private int quantity;
	private BigDecimal price;
	private Integer spice;
	private String amountName;
	private String spiceName;
	private Integer amount;

	// 생성자
	public CartItem( int menuId, int quantity, BigDecimal price, String amountName,Integer spice,String spiceName, Integer amount) {
		this.menuId = menuId;
		this.quantity = quantity;
		this.price = price;
		this.spice = spice;
		this.amount = amount;
		this.amountName = amountName;
		this.spiceName = spiceName;
	}
	public CartItem( MenuDTO menu, int quantity, String amountName, String spiceName, Integer amount, Integer spice) {
		this.menuId = menu.getMenuId();
		this.menuName = menu.getName();
		this.quantity = quantity;
		this.price = menu.getPrice();
		this.spice =spice;
		this.amount = amount;
		this.amountName = amountName;
		this.spiceName = spiceName;
	}

	public CartItem() {

	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setMenuId(String menuName) {
		this.menuName = menuName;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
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
		return "CartItem{" +
			", menuId=" + menuId +
			", quantity=" + quantity +
			", price=" + price +
			", spice=" + spice +
			", amount=" + amount +
			'}';
	}
}

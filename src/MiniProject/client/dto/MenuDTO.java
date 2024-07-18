package MiniProject.client.dto;

import java.math.BigDecimal;

public class MenuDTO {
	private int menuId;
	private int storeId;
	private String name;
	private BigDecimal price;
	private String category;
	private String spice;
	private String amount;

	public MenuDTO() {
	}

	public MenuDTO(int menuId, int storeId, String name, BigDecimal price, String category, String spice, String amount) {
		this.menuId = menuId;
		this.storeId = storeId;
		this.name = name;
		this.price = price;
		this.category = category;
		this.spice = spice;
		this.amount = amount;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSpice() {
		return spice;
	}

	public void setSpice(String spice) {
		this.spice = spice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "MenuDTO{" +
			"menuId=" + menuId +
			", storeId=" + storeId +
			", name='" + name + '\'' +
			", price=" + price +
			", category=" + category +
			", spice='" + spice + '\'' +
			", amount='" + amount + '\'' +
			'}';
	}
}

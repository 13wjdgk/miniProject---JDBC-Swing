package MiniProject.client.dto;

import java.time.LocalDateTime;

public class StoreDTO {
	private int storeId;
	private String name;
	private String address;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public StoreDTO() {

	}

	public StoreDTO(int storeId, String name, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.storeId = storeId;
		this.name = name;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "StoreDTO{" +
			"storeId=" + storeId +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			'}';
	}
}

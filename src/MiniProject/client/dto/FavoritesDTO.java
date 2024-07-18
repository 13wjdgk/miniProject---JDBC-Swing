package MiniProject.client.dto;


import java.sql.Timestamp;

public class FavoritesDTO {
	private int userId;
	private int storeId;
	private Timestamp favoritedAt;

	public FavoritesDTO() {
	}

	public FavoritesDTO(int userId, int storeId, Timestamp favoritedAt) {
		this.userId = userId;
		this.storeId = storeId;
		this.favoritedAt = favoritedAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Timestamp getFavoritedAt() {
		return favoritedAt;
	}

	public void setFavoritedAt(Timestamp favoritedAt) {
		this.favoritedAt = favoritedAt;
	}

	@Override
	public String toString() {
		return "FavoritesDTO{" +
			"userId=" + userId +
			", storeId=" + storeId +
			", favoritedAt=" + favoritedAt +
			'}';
	}
}

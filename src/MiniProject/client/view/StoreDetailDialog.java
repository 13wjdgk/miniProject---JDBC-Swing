package MiniProject.client.view;

import java.awt.*;

import javax.swing.*;

import MiniProject.client.dao.FavoritesDao;
import MiniProject.client.dao.StoreDao;
import MiniProject.client.dto.StoreDTO;
import MiniProject.client.dto.User;

public class StoreDetailDialog extends JDialog {
	private StoreDao storeDao = new StoreDao();
	private FavoritesDao favoritesDao = new FavoritesDao();

	private JButton callButton, LikeButton;

	public StoreDetailDialog(JFrame frame, int storeId , User user) {
		super(frame, storeId + " 상세 정보", true);
		setSize(300, 500);
		setLocationRelativeTo(frame);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		StoreDTO store = getStoreDetail(storeId);
		JLabel nameLabel = new JLabel("가게 이름: " + store.getName());
		JLabel addressLabel = new JLabel("가게 주소: "+ store.getAddress() );


		add(nameLabel);
		add(addressLabel);
		JPanel buttonPanel = new JPanel();

		callButton = new JButton("☎ 전화하기");
		boolean isFavorite  = isFavorite(storeId,user.getUserId());
		if(isFavorite){
			LikeButton = new JButton("★ 찜했음");
		}else{
			LikeButton = new JButton("☆ 찜하기");
		}


		LikeButton.addActionListener(e -> {
			setLikeButton(storeId,user.getUserId(),isFavorite(storeId,user.getUserId()));
		});


		buttonPanel.add(LikeButton);
		buttonPanel.add(callButton);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
	}
	private StoreDTO getStoreDetail(int storeId){
		return storeDao.findStoreById(storeId);
	}
	private boolean isFavorite(int storeId, int userId){
		return favoritesDao.findLikeByUserIdAndStoreId(userId,storeId);
	}
	private void setLikeButton (int storeId,int userId,boolean isFavorite){
		favoritesDao.setFavorite(storeId,userId,isFavorite);
		if(isFavorite){
			LikeButton.setText("☆ 찜하기");
		}else{
			LikeButton.setText("★ 찜하기");

		}

	}
}

package MiniProject.client.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MiniProject.client.dao.FavoritesDao;
import MiniProject.client.dao.MenuDao;
import MiniProject.client.dao.StoreDao;
import MiniProject.client.dto.MenuDTO;
import MiniProject.client.dto.StoreDTO;
import MiniProject.client.dto.User;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreDetailDialog extends JFrame {
	private StoreDao storeDao = new StoreDao();
	private FavoritesDao favoritesDao = new FavoritesDao();
	private MenuDao menuDao = new MenuDao();

	private User user;

	private JButton likeButton, callButton;
	private JPanel menuPanel;

	public StoreDetailDialog(JFrame frame, int storeId, User user) {
		this.user = user;

		// 가게 정보 패널
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		// 가게 이름, 주소 추가
		StoreDTO store = getStoreDetail(storeId);
		JLabel nameLabel = new JLabel("가게 이름: " + store.getName());
		JLabel addressLabel = new JLabel("가게 주소: " + store.getAddress());
		infoPanel.add(nameLabel);
		infoPanel.add(addressLabel);

		// 찜하기 버튼
		likeButton = new JButton("☆ 찜하기");
		likeButton.addActionListener(e -> {
			boolean isFavorite = isFavorite(storeId, user.getUserId());
			setLikeButton(storeId, user.getUserId(), isFavorite);
		});
		infoPanel.add(likeButton);

		// 전화하기 버튼
		callButton = new JButton("☎ 전화하기");
		callButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phoneNumber = "010-0000-0000";
				if (!phoneNumber.isEmpty()) {
					try {
						// MacOS에서는 아래와 같이 URI scheme을 tel: 로 사용하여 전화 앱을 실행할 수 있습니다.
						URI uri = new URI("tel:" + phoneNumber);
						Desktop.getDesktop().browse(uri);
					} catch (URISyntaxException | IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(StoreDetailDialog.this,
							"전화 앱을 실행하는데 문제가 발생했습니다.",
							"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(StoreDetailDialog.this,
						"전화 번호를 입력해주세요.",
						"경고", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		infoPanel.add(callButton);

		getContentPane().add(infoPanel, BorderLayout.NORTH);

		// 메뉴 목록 패널
		menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		loadMenuList(storeId);
		JScrollPane scrollPane = new JScrollPane(menuPanel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setSize(400, 600);
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	private StoreDTO getStoreDetail(int storeId) {
		return storeDao.findStoreById(storeId);
	}

	private boolean isFavorite(int storeId, int userId) {
		return favoritesDao.findLikeByUserIdAndStoreId(userId, storeId);
	}

	private void setLikeButton(int storeId, int userId, boolean isFavorite) {
		favoritesDao.setFavorite(storeId, userId, isFavorite);
		if (isFavorite) {
			likeButton.setText("☆ 찜하기");
		} else {
			likeButton.setText("★ 찜했음");
		}
	}

	private void loadMenuList(int storeId) {
		menuPanel.removeAll(); // 기존 메뉴 목록 제거

		List<MenuDTO> menus = menuDao.findMenuListByStoreId(storeId);
		Map<String, DefaultTableModel> categoryTables = new HashMap<>();

		// 각 카테고리 별로 메뉴를 테이블에 추가
		for (MenuDTO menu : menus) {
			String category = menu.getCategory();

			// 카테고리 테이블 생성 또는 가져오기
			DefaultTableModel categoryTableModel = categoryTables.computeIfAbsent(category, k -> {
				// 카테고리 레이블 생성
				JLabel categoryLabel = new JLabel("카테고리: " + category);
				categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
				categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

				// 테이블 모델 생성
				DefaultTableModel model = new DefaultTableModel(new Object[]{"메뉴 이름", "가격"}, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; // 테이블 셀 수정 불가능하도록 설정
					}
				};

				// 테이블 생성 및 설정
				JTable categoryTable = new JTable(model);
				categoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
				// JTable에 MouseListener 추가
				categoryTable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) { // 더블 클릭인 경우
							int row = categoryTable.rowAtPoint(e.getPoint());
							int column = categoryTable.columnAtPoint(e.getPoint());
							if (row >= 0 && column >= 0) {
								Object value = categoryTable.getValueAt(row, column);
								MenuSelectDialog dialog = new MenuSelectDialog( StoreDetailDialog.this, storeId,user , value.toString());
							}
						}
					}
				});

				// 테이블 패널 생성 및 설정
				JPanel tablePanel = new JPanel(new BorderLayout());
				tablePanel.add(categoryLabel, BorderLayout.NORTH);
				tablePanel.add(categoryTable, BorderLayout.CENTER);

				// 메뉴 패널에 추가
				menuPanel.add(tablePanel);


				return model;
			});

			// 메뉴 행 추가
			categoryTableModel.addRow(new Object[]{menu.getName(), menu.getPrice().toBigInteger()});

		}

		revalidate();
		repaint();
	}

}

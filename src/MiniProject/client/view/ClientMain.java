package MiniProject.client.view;



import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import MiniProject.client.dao.StoreDao;
import MiniProject.client.dao.UserDao;
import MiniProject.client.dto.CartList;
import MiniProject.client.dto.StoreDTO;
import MiniProject.client.dto.User;

public class ClientMain extends JFrame {
	public static CartList cart ;
	private User user;
	private static UserDao userDao = new UserDao();
	private static StoreDao storeDao = new StoreDao();
	private JTable storeTable;
	private DefaultTableModel tableModel;


	public ClientMain(User user) {
		this.user = user;
		initializeUI();
	}

	private void initializeUI() {
		setTitle("UPlus 배달의 민족 - " + user.getEmail());
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 예시로 기본적인 UI 요소 추가
		JLabel welcomeLabel = new JLabel("<html>환영합니다 ," + user.getEmail() + "님!<br>어떤 음식을 주문하시겠어요?</html>");

		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel, BorderLayout.CENTER);

		// 테이블 모델과 테이블 생성
		String[] columnNames = {"ID", "이름", "주소"};
		tableModel = new DefaultTableModel(columnNames, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		storeTable = new JTable(tableModel);
		storeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(storeTable);
		add(scrollPane, BorderLayout.CENTER);

		// 가게 목록 가져오기
		List<StoreDTO> stores = storeDao.findAll();
		for (StoreDTO store : stores) {
			Object[] rowData = {store.getStoreId(), store.getName(), store.getAddress()};
			tableModel.addRow(rowData);
		}

		// 테이블 클릭 이벤트 처리
		storeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedRow = storeTable.getSelectedRow();
				if (selectedRow != -1) {
					int storeId = (int)storeTable.getValueAt(selectedRow, 0);
					StoreDetailDialog dialog = new StoreDetailDialog(ClientMain.this , storeId,user);
				}
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton cartButton = new JButton("장바구니");
		JButton myPageButton = new JButton("마이페이지");
		JButton orderHistoryButton = new JButton("주문내역");

		buttonPanel.add(cartButton);
		buttonPanel.add(myPageButton);
		buttonPanel.add(orderHistoryButton);

		cartButton.addActionListener(e -> {
			CartDialog cartDialog = new CartDialog(ClientMain.this, user);
		});
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			cart = new CartList();
			// 첫 화면으로 이메일 입력 다이얼로그를 띄우기
			String email = JOptionPane.showInputDialog(null, "이메일을 입력하세요:", "이메일 입력", JOptionPane.PLAIN_MESSAGE);

			// 입력된 이메일이 null이 아니고 빈 문자열이 아닌 경우 처리
			if (email != null && !email.trim().isEmpty()) {
				// 유저 확인
				User user = userDao.findUserByEmail(email);
				if(user == null) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 이메일입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				ClientMain clientMain = new ClientMain(user);
			} else {
				JOptionPane.showMessageDialog(null, "이메일 입력이 취소되었거나 유효하지 않습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
		});
	}
}

package MiniProject.client.view;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

import MiniProject.client.dao.OrderDao;
import MiniProject.client.dto.CartItem;
import MiniProject.client.dto.CarItem;
import MiniProject.client.dto.User;
import MiniProject.client.enums.OrderState;

public class OrderHistoryDetailDialog extends JFrame {
	private OrderDao orderDao = new OrderDao();

	// Components
	private JLabel lblOrderId;
	private JLabel lblStoreName;
	private JLabel lblUserAddress;
	private JLabel lblOrderDate;
	private JLabel lblOrderStatus;
	private JTable menuTable;
	private JButton btnCancelOrder;
	private JButton btnWriteReview;
	private DefaultTableModel tableModel;


	public OrderHistoryDetailDialog(JFrame frame, User user, CarItem orderHistory, OrderHistoryDialog parentDialog) {
		setTitle("주문 상세 정보");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null); // 화면 가운데 정렬

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(5, 2, 10, 5));
		contentPane.add(infoPanel, BorderLayout.NORTH);

		lblOrderId = new JLabel("주문번호: " + orderHistory.getOrderId());
		infoPanel.add(lblOrderId);

		lblStoreName = new JLabel("가게이름: " + orderHistory.getStoreName());
		infoPanel.add(lblStoreName);

		lblUserAddress = new JLabel("배달 주소: " + orderHistory.getDeliveryAddress());
		infoPanel.add(lblUserAddress);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분");
		lblOrderDate = new JLabel("주문날짜: " + orderHistory.getOrderDate());
		infoPanel.add(lblOrderDate);

		lblOrderStatus = new JLabel("주문 진행 상황: " + orderHistory.getState());
		infoPanel.add(lblOrderStatus);

		// 메뉴 테이블 설정
		menuTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(menuTable); // JScrollPane에 메뉴 테이블 추가
		contentPane.add(scrollPane, BorderLayout.CENTER); // JScrollPane를 contentPane에 추가

		String[] columnNames = {"메뉴 이름", "수량", "가격", "매운 정도", "사이즈"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		menuTable.setModel(tableModel); // 테이블 모델 설정

		// 장바구니 내용 채우기
		loadCartItems(orderHistory.getOrderId());



		if(orderHistory.getState().equals(OrderState.접수중.getState())){
			btnCancelOrder = new JButton("주문 취소");
			btnCancelOrder.addActionListener(e -> {
				// 주문 취소 기능 추가
				JOptionPane.showMessageDialog(this, "주문을 취소합니다.");
				if(orderDao.cancelOrder(orderHistory.getOrderId())){
					JOptionPane.showMessageDialog(this, "주문이 취소되었습니다.");
					parentDialog.refreshOrderHistory(user);
				}else{
					JOptionPane.showMessageDialog(this, "주문 취소에 실패했습니다.");
				}
				dispose();

			});
			contentPane.add(btnCancelOrder, BorderLayout.SOUTH);
		} else if (orderHistory.getState().equals(OrderState.주문완료.getState())) {
			// 리뷰 쓰기 버튼
			btnWriteReview = new JButton("리뷰 쓰기");
			btnWriteReview.addActionListener(e -> {
				ReviewFrame reviewFrame = new ReviewFrame(orderHistory.getStoreId(), user.getUserId(), orderHistory.getOrderId());

			});
			contentPane.add(btnWriteReview, BorderLayout.SOUTH);
		}
		// // 주문 취소 버튼

		setVisible(true);
	}

	private void loadCartItems(int orderId) {
		tableModel.setRowCount(0);
		for (CartItem item : orderDao.findOrderMenusHistoryById(orderId)) {
			System.out.println(item.getMenuName() + " " + item.getQuantity() + " " + item.getPrice() + " " + item.getSpiceName() + " " + item.getAmountName());
			Object[] rowData = {
				item.getMenuName(),
				item.getQuantity(),
				item.getPrice().intValue(),
				item.getSpiceName(),
				item.getAmountName()
			};
			tableModel.addRow(rowData);
		}
	}




}

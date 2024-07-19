package MiniProject.client.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import MiniProject.client.dao.OrderDao;
import MiniProject.client.dto.CarItem;
import MiniProject.client.dto.User;

public class OrderHistoryDialog extends JFrame {
	private JTable orderTable;
	private OrderDao orderDao = new OrderDao();
	private DefaultTableModel model;

	public OrderHistoryDialog(JFrame frame, User user) {
		setTitle("Order History");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 600);

		JPanel mainPanel = new JPanel(new BorderLayout());

		model = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("주문번호");
		model.addColumn("가게 이름");
		model.addColumn("주문 날짜");
		model.addColumn("주문 진행 상황");


		// Create table with model
		orderTable = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(orderTable);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		// 가게 목록 가져오기
		List<CarItem> orders = getCarItems(user);

		// 테이블 클릭 이벤트 처리
		orderTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedRow = orderTable.getSelectedRow();
				if (selectedRow != -1) {
					CarItem orderHistory = orders.get(selectedRow);

					OrderHistoryDetailDialog orderHistoryDetailDialog = new OrderHistoryDetailDialog(frame, user,orderHistory,OrderHistoryDialog.this);
				}
			}
		});

		// Add main panel to frame
		getContentPane().add(mainPanel);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private List<CarItem> getCarItems(User user) {
		model.setRowCount(0);
		List<CarItem> orders = orderDao.findOrderHistoryById(user.getUserId());
		for (CarItem order : orders) {

			String orderDate = formatToLocalDateTime(order);

			Object[] rowData = {order.getOrderId(), order.getStoreName(), orderDate,order.getState()};
			model.addRow(rowData);
		}
		return orders;
	}

	private static String formatToLocalDateTime(CarItem order) {
		LocalDateTime localDateTime = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		// DateTimeFormatter를 이용하여 원하는 형식으로 변환
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분");
		String orderDate = localDateTime.format(formatter);
		return orderDate;
	}


	public void refreshOrderHistory(User user) {
		getCarItems(user); // 주문 목록 다시 조회
		model.fireTableDataChanged(); // 테이블 모델에 변경 사항을 알림
	}

}

package MiniProject.client.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import MiniProject.client.dao.OrderDao;
import MiniProject.client.dto.CartItem;
import MiniProject.client.dto.CartList;

import MiniProject.client.dto.User;

public class CartDialog extends JFrame {
	private CartList cart;
	private OrderDao orderDao = new OrderDao();
	private JTable cartTable;
	private DefaultTableModel tableModel;
	private JButton removeButton, checkoutButton;

	public CartDialog(JFrame frame, User user) {
		cart = CartList.getinstance();
		initializeUI(frame, user);
	}

	private void initializeUI(JFrame frame, User user) {
		setTitle("장바구니");
		setSize(500, 400);
		setLocationRelativeTo(frame);

		String[] columnNames = {"메뉴 이름", "수량", "가격", "매운 정도", "사이즈"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		cartTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(cartTable);
		add(scrollPane, BorderLayout.CENTER);

		// 장바구니 내용 채우기
		loadCartItems();

		JPanel buttonPanel = new JPanel();
		removeButton = new JButton("선택 항목 제거");
		checkoutButton = new JButton("주문하기");

		removeButton.addActionListener(e -> removeSelectedItem());
		checkoutButton.addActionListener(e -> {
			List<CartItem> list = cart.getCartList();
			int totalPrice = Arrays.stream(list.toArray())
				.mapToInt(item -> ((CartItem) item).getPrice().intValue()*((CartItem) item).getQuantity())
				.sum();
			checkout(user.getUserId(), 1, totalPrice, list);

		});

		buttonPanel.add(removeButton);
		buttonPanel.add(checkoutButton);

		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void loadCartItems() {
		tableModel.setRowCount(0);
		for (CartItem item : cart.getCartList()) {
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

	private void removeSelectedItem() {
		int selectedRow = cartTable.getSelectedRow();
		if (selectedRow != -1) {
			cart.getCartList().remove(selectedRow);
			tableModel.removeRow(selectedRow);
		} else {
			JOptionPane.showMessageDialog(this, "제거할 항목을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void checkout(int userId, int storeId, Integer totalPrice, List<CartItem> cartItems) {
		LocalDateTime saveTime = LocalDateTime.now();
		int orderId = orderDao.insertOrder(userId, storeId, totalPrice, saveTime,cartItems);
		// 주문 처리 로직 추가
		if(orderId > 0){
			if(orderDao.insertOderItems(orderId,userId, storeId, totalPrice, saveTime, cartItems)){
				JOptionPane.showMessageDialog(this, "주문이 완료되었습니다.");
				cart.getCartList().clear();
				tableModel.setRowCount(0);
				return;
			}
		}
		JOptionPane.showMessageDialog(this, "주문에 실패했습니다.");
	}
}

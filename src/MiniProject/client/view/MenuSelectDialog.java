package MiniProject.client.view;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import MiniProject.client.dao.MenuCommonCodeDao;
import MiniProject.client.dao.MenuDao;
import MiniProject.client.dto.CartItem;
import MiniProject.client.dto.CartList;
import MiniProject.client.dto.MenuCommonCodeDTO;
import MiniProject.client.dto.MenuDTO;
import MiniProject.client.dto.OrderItemDTO;
import MiniProject.client.dto.User;

public class MenuSelectDialog extends JDialog {


	private MenuDao menuDao = new MenuDao();
	private MenuCommonCodeDao menuCommonCodeDao = new MenuCommonCodeDao();
	private JRadioButton[] spiceRadioButtons,amountRadioButtons;
	private ButtonGroup spiceButtonGroup,amountButtonGroup;
	private Map<String , Integer> spiceMap = new HashMap<>();
	private Map<String , Integer> amountMap = new HashMap<>();
	private JButton saveButton;
	public MenuSelectDialog(JFrame frame,int storeId, User user , String menuName) {
		CartList cart = CartList.getinstance();

		// 메뉴 정보 패널
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		// 가게 이름, 주소 추가
		MenuDTO menu = getMenuDetail(storeId,menuName);
		JLabel nameLabel = new JLabel("메뉴 이름: " + menu.getName());
		JLabel addressLabel = new JLabel("메뉴 가격: " + menu.getPrice());


		infoPanel.add(nameLabel);
		infoPanel.add(addressLabel);


		if(menu.getSpice() != null) {
			JLabel spiceLabel = new JLabel("맵기 선택 ");
			infoPanel.add(spiceLabel);
			List<MenuCommonCodeDTO> options = getSpiceOptions(menu.getSpice());
			spiceRadioButtons = new JRadioButton[options.size()];
			spiceButtonGroup = new ButtonGroup();
			// 라디오 버튼 초기화
			for (int i = 0; i < options.size(); i++) {
				MenuCommonCodeDTO option = options.get(i);
				spiceRadioButtons[i] = new JRadioButton(option.getName());
				spiceRadioButtons[i].setActionCommand(option.getName()); // 옵션 코드 설정 (예시로 코드 ID 사용)
				spiceRadioButtons[i].addActionListener(e -> {

				});

				// 버튼 그룹에 추가
				spiceButtonGroup.add(spiceRadioButtons[i]);
				infoPanel.add(spiceRadioButtons[i]);
			}
		}
		if(menu.getAmount() != null) {
			JLabel amountLabel = new JLabel("사이즈 선택 ");
			List<MenuCommonCodeDTO> options = getAmountOptions(menu.getAmount());
			infoPanel.add(amountLabel);
			amountRadioButtons = new JRadioButton[options.size()];
			amountButtonGroup = new ButtonGroup();
			// 라디오 버튼 초기화
			for (int i = 0; i < options.size(); i++) {
				MenuCommonCodeDTO option = options.get(i);
				amountRadioButtons[i] = new JRadioButton(option.getName());
				amountRadioButtons[i].setActionCommand(option.getName()); // 옵션 코드 설정 (예시로 코드 ID 사용)
				amountRadioButtons[i].addActionListener(e -> {

				});

				// 버튼 그룹에 추가
				amountButtonGroup.add(amountRadioButtons[i]);
				infoPanel.add(amountRadioButtons[i]);
			}
		}




		saveButton = new JButton("담기");
		saveButton.addActionListener(e -> {
			// 선택된 라디오 버튼의 옵션 코드를 가져옴
			String spice = null;
			String amount = null;
			if(menu.getSpice() != null) {
				spice = spiceButtonGroup.getSelection().getActionCommand();
			}
			if(menu.getAmount() != null) {
				amount = amountButtonGroup.getSelection().getActionCommand();
			}

			// 선택된 옵션 코드를 이용해 DB에 저장
			if(!cart.saveItem(new CartItem(menu,1,amount ,spice, amount!=null?amountMap.get(amount):null,spice!=null?spiceMap.get(spice):null),storeId)){
				JOptionPane.showMessageDialog(this, "장바구니에 담긴 다른 가게 메뉴를 제거해 주세요");
				return;
			}
			JOptionPane.showMessageDialog(this, "장바구니에 담겼습니다.");

			dispose();
		});
		getContentPane().add(infoPanel, BorderLayout.NORTH);
		add(saveButton, BorderLayout.SOUTH);
		setSize(400, 600);
		setLocationRelativeTo(frame);
		setVisible(true);

	}
	public MenuDTO getMenuDetail(int storeId, String menuName) {
		return menuDao.findMenuByStoreIdAndName(storeId,menuName);
	}
	public List<MenuCommonCodeDTO> getSpiceOptions(String codeTypeId) {
		menuCommonCodeDao.findCommonCodeByCodeType(codeTypeId).forEach( option -> {
			spiceMap.put(option.getName(), option.getCodeId());
		});

		return menuCommonCodeDao.findCommonCodeByCodeType(codeTypeId);
	}

	public List<MenuCommonCodeDTO> getAmountOptions(String codeTypeId) {
		menuCommonCodeDao.findCommonCodeByCodeType(codeTypeId).forEach( option -> {
			amountMap.put(option.getName(), option.getCodeId());
		});

		return menuCommonCodeDao.findCommonCodeByCodeType(codeTypeId);
	}
}

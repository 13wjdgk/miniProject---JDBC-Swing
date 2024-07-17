package Practice.swingBook;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Practice.swingBook.dto.Book;

public class EditBookDialog extends JDialog {
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton updateButton,deleteButton;
	private DefaultTableModel tableModel;
	public EditBookDialog(BookManager parent , DefaultTableModel tableModel,int rowIndex){
		this.tableModel = tableModel;
		setTitle("Book Edit Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);

		//선택된 row의 각 항목의 값을 구하고 JTextFile 객체를 생성하면서 값을 전달
		Integer bookId = (Integer)tableModel.getValueAt(rowIndex, 0);
		Book book = parent.getDetailBook(bookId);

		//input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4,2));

		bookIdField = new JTextField(String.valueOf(book.getBookId()));
		bookIdField.setEditable(false);
		bookNameField = new JTextField(book.getBookName());
		publisherField = new JTextField(book.getPublisher());
		priceField = new JTextField(String.valueOf(book.getPrice()));

		updateButton = new JButton("SAVE");

		inputPanel.add(new JLabel("Book ID"));
		inputPanel.add(bookIdField);
		inputPanel.add(new JLabel("Book Name"));
		inputPanel.add(bookNameField);
		inputPanel.add(new JLabel("Publisher"));
		inputPanel.add(publisherField);
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);

		//button panel
		JPanel buttonPanel = new JPanel();

		deleteButton = new JButton("DELETE");
		updateButton = new JButton("UPDATE");

		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		//add input panel and button panel to the dialog
		add(inputPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

		updateButton.addActionListener(e -> {
			int ret = JOptionPane.showConfirmDialog(this, "수정할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
			if( ret == JOptionPane.YES_OPTION ) {
				int currentBookId = Integer.parseInt(bookIdField.getText());
				String bookName = bookNameField.getText();
				String publisher = publisherField.getText();
				int price = Integer.parseInt(priceField.getText());

				parent.updateBook(new Book(bookId, bookName, publisher, price));
				dispose();

			}
			dispose();

		});
		deleteButton.addActionListener(e -> {
			int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
			if( ret == JOptionPane.YES_OPTION ) {


				parent.deleteBook(bookId);
				dispose();

			}
			dispose();

		});
	}
}

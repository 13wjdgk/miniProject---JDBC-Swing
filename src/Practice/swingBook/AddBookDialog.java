package Practice.swingBook;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Practice.swingBook.dto.Book;

public class AddBookDialog extends JDialog {
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton addButton, cancelButton;
	private DefaultTableModel tableModel;
	public AddBookDialog(BookManager parent , DefaultTableModel tableModel){
		this.tableModel = tableModel;
		setTitle("AddBookDialog");
		setSize(300, 200);

		setLocationRelativeTo(parent);

		//field panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4,2));



		bookIdField = new JTextField();
		bookNameField = new JTextField();
		publisherField = new JTextField();
		priceField = new JTextField();

		addButton = new JButton("Add");

		inputPanel.add(new JLabel("Book ID"));
		inputPanel.add(bookIdField);
		inputPanel.add(new JLabel("Book Name"));
		inputPanel.add(bookNameField);
		inputPanel.add(new JLabel("Publisher"));
		inputPanel.add(publisherField);
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);

		JPanel buttonPanel = new JPanel();
		addButton = new JButton("Add");
		buttonPanel.add(addButton);

		add(inputPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

		addButton.addActionListener(e -> {
			Integer bookId = Integer.parseInt(bookIdField.getText());
			String bookName = bookNameField.getText();
			String publisher = publisherField.getText();
			Integer price = Integer.parseInt(priceField.getText());
			parent.insertBook(new Book(bookId, bookName, publisher, price));
			dispose();

		});
	}
}

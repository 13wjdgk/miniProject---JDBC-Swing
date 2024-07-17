package Practice.swingBook;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Practice.swingBook.dao.BookDao;
import Practice.swingBook.dto.Book;

public class BookManager extends JFrame {
	private JTable table;
	private static DefaultTableModel tableModel;
	private JButton addButton , editButton, listButton , searchButton;

	private JTextField searchField;

	private BookDao bookDao = new BookDao();
	public BookManager() {
		setTitle("Book Manager");
		setSize(600, 400);
		// 언제 종료할 것인가 ?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//table
		tableModel = new DefaultTableModel(new Object[]{"Book ID","Book Name","Publisher","price"},0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are not editable
			}
		};
		table = new JTable(tableModel);

		listBook();

		Dimension textFieldSize = new Dimension(400, 28);
		searchField = new JTextField();
		searchField.setPreferredSize(textFieldSize);

		searchButton = new JButton("검색");
		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("제목 검색"));
		searchPanel.add(searchField);
		searchPanel.add(searchButton);



		//button
		addButton = new JButton("등록");
		editButton = new JButton("수정 & 삭제");
		listButton = new JButton("목록");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(listButton);


		//BookManager의 layout 설정
		setLayout(new BorderLayout());
		//add(table, BorderLayout.CENTER);
		add(new JScrollPane(table), BorderLayout.CENTER); //table < scroll pane < jfram (데이터가 많아지면 스크롤됨)
		add(buttonPanel, BorderLayout.SOUTH);
		add(searchPanel, BorderLayout.NORTH);


		//button action event 처리
		addButton.addActionListener( e -> {
			AddBookDialog addBookDialog = new AddBookDialog(this,tableModel);
			addBookDialog.setVisible(true);

		});

		editButton.addActionListener( e -> {
			int selectedRow = table.getSelectedRow();
			if(selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Please select a row to edit");
				return;
			}
			EditBookDialog editBookDialog = new EditBookDialog(this,tableModel,selectedRow);
			editBookDialog.setVisible(true);

		});

		listButton.addActionListener( e -> {
			listBook();
			//addBookDialog.setVisible(true);

		});

		searchButton.addActionListener( e ->{
			String searchWord = searchField.getText();
			if(!searchWord.isBlank()) {
				listBook(searchWord);
			}
		});


		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// double click
				if (e.getClickCount() == 2) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow >= 0) {
						EditBookDialog editDialog = new EditBookDialog(BookManager.this, tableModel, selectedRow);
						editDialog.setVisible(true);
					}
				}
			}
		});
	}
	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void listBook(){
		clearTable();
		List<Book> bookList = bookDao.listBook();
		for(Book book : bookList) {
			tableModel.addRow(new Object[] {book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
		}
	}
	private void listBook(String searchWord){
		clearTable();
		List<Book> bookList = bookDao.listBook(searchWord);
		for(Book book : bookList) {
			tableModel.addRow(new Object[] {book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
		}
	}
	Book getDetailBook(int bookId) {
		return bookDao.detailBook(bookId);
	}

	void updateBook(Book book) {
		int ret = bookDao.updateBook(book);
	}
	void deleteBook(int bookId) {
		int ret = bookDao.deleteBook(bookId);
	}
	void insertBook(Book book) {
		int ret = bookDao.insertBook(book);
	}
	public static void main(String[] args) {
		// main 스레드와 별개로 별도의 스레드로 화면을 띄운다.
		// 스레드 처리를 간단히 해주는 utility method 제공
		// Runnable 인터페이스를 구현한 객체를 받아서 스레드를 생성하고 실행
		SwingUtilities.invokeLater(() -> {
			BookManager bookManager = new BookManager();
			bookManager.setVisible(true);
		});
	}
}

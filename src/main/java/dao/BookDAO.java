package dao;

import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import constant.Constant;
import db.DBUtil;
import entity.Book;
import entity.Category;

public class BookDAO {

	public int getTotalBooks() throws SQLException {
		Constant.TOTAL_BOOKS = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT COUNT(*) AS total_books FROM book";

			conn = DBUtil.makeConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			rs.next();

			Constant.TOTAL_BOOKS = rs.getInt("total_books");

			return Constant.TOTAL_BOOKS;

		} catch (Exception e) {

		} finally {

			close(conn, ps, rs);

		}

		return Constant.TOTAL_BOOKS;

	}

	public List<Book> getAllBooks(int currentPage) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		try {

			conn = DBUtil.makeConnection();

			currentPage = (currentPage - 1) * Constant.BOOK_PER_PAGE;

			String sql = "SELECT * FROM book LIMIT ? OFFSET ?";

			// ps contains sql and parameter values
			ps = conn.prepareStatement(sql);

			ps.setInt(1, Constant.BOOK_PER_PAGE);
			ps.setInt(2, currentPage);

			rs = ps.executeQuery();

			// rs -> record or many records
			// rs.next will return boolean if data exists for the first results we will get
			// them and put into Student entity
			while (rs.next()) { // rs.next is boolean 1/0

				book = new Book();

				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setCategoryId(rs.getInt("category_id"));
				book.setDescription(rs.getString("description"));
				book.setStock(rs.getInt("stock"));

				books.add(book);
			}

			return books;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return null;
	}

	public int getTotalBooksByCategory(int categoryID) throws SQLException {
		Constant.TOTAL_BOOKS = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();

			String sql = "SELECT COUNT(*) AS total_books FROM book WHERE book.category_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, categoryID);
			rs = ps.executeQuery();

			rs.next();
			Constant.TOTAL_BOOKS = rs.getInt("total_books");
			System.out.println(Constant.TOTAL_BOOKS);
			return Constant.TOTAL_BOOKS;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return Constant.TOTAL_BOOKS;
	}

	public List<Book> getBooksByCategory(int categoryID, int currentPage) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		try {

			conn = DBUtil.makeConnection();

			currentPage = (currentPage - 1) * Constant.BOOK_PER_PAGE;

			String sql = "SELECT * FROM book WHERE book.category_id = ? LIMIT ? OFFSET ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, categoryID);
			ps.setInt(2, Constant.BOOK_PER_PAGE);
			ps.setInt(3, currentPage);

			rs = ps.executeQuery();

			while (rs.next()) {

				book = new Book();

				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setCategoryId(rs.getInt("category_id"));
				book.setDescription(rs.getString("description"));
				book.setStock(rs.getInt("stock"));

				books.add(book);
			}
			return books;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return null;
	}

	public Book getBookDetails(int bookID) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book bookDetails = null;
		// List<Book> booksDetails = new ArrayList<Book>();

		try {
			// make connection to mysql
			conn = DBUtil.makeConnection();

			System.out.println("conn " + conn);
			String sql = "SELECT * FROM book WHERE book.id = ?";

			// ps contains sql and parameter values
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bookID);
			rs = ps.executeQuery();

			// rs -> record or many records
			// rs.next will return boolean if data exists for the first results we will get
			// them and put into Student entity
			while (rs.next()) { // rs.next is boolean 1/0

				bookDetails = new Book();

				bookDetails.setId(rs.getInt("id"));
				bookDetails.setName(rs.getString("name"));
				bookDetails.setCategoryId(rs.getInt("category_id"));
				bookDetails.setDescription(rs.getString("description"));
				bookDetails.setStock(rs.getInt("stock"));

			}
			return bookDetails;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return null;
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {

		if (conn != null) {
			conn.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}

	}

}

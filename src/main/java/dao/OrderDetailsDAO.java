package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.CartServlet;
import db.DBUtil;
import entity.Book;
import entity.Order;
import entity.OrderDetails;

public class OrderDetailsDAO {

	public void insertOrder(int studentId, List<Book> books) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int orderId = 0;

		try {

			conn = DBUtil.makeConnection();
			conn.setAutoCommit(false);

			String sql = "INSERT INTO `order` (student_id, submit_date) VALUES(?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, studentId);
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

			sql = "INSERT INTO `order_details` (order_id, book_id) VALUES (?,?)";

			pstm = conn.prepareStatement(sql);

			for (Book book : books) {

				pstm.setInt(1, orderId);
				pstm.setInt(2, book.getId());
				pstm.addBatch();

			}
			pstm.executeBatch();

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {

			close(conn, ps, rs);
			if (pstm != null) {
				pstm.close();
			}

		}

	}

	public void insertOrderDetails(int orderId, List<Book> books) throws SQLException {

		// Connection conn = null;
		// PreparedStatement ps = null;
		// ResultSet rs = null;

		/*
		 * try {
		 * 
		 * conn = DBUtil.makeConnection();
		 * 
		 * String sql = "INSERT INTO `order_details` (order_id, book_id) value(?,?)";
		 * 
		 * ps = conn.prepareStatement(sql);
		 * 
		 * ps.setInt(1, orderDetails.getOrderId()); ps.setInt(2,
		 * orderDetails.getBookid());
		 * 
		 * ps.execute();
		 * 
		 * } catch (Exception e) {
		 * 
		 * } finally {
		 * 
		 * close(conn, ps, rs);
		 * 
		 * }
		 */

		// conn = DBUtil.makeConnection();

		String sql = "INSERT INTO `order_details` (order_id, book_id) value(?,?)";

		CartServlet.psOrderDetails = CartServlet.orderConn.prepareStatement(sql);

		for (Book b : books) {

			CartServlet.psOrderDetails.setInt(1, orderId);
			CartServlet.psOrderDetails.setInt(2, b.getId());

			CartServlet.psOrderDetails.addBatch();
		}

		CartServlet.psOrderDetails.executeBatch();
		// conn.commit();

		/*
		 * finally {
		 * 
		 * close(conn, ps, rs);
		 * 
		 * }
		 */
	}

	public List<OrderDetails> viewOrderDetails(int orderId) throws SQLException {

		List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		OrderDetails orderDetail;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.makeConnection();

			String sql = "SELECT * FROM `order_details` LEFT JOIN book ON `order_details`.book_id = book.id WHERE `order_details`.order_id = ?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, orderId);

			rs = ps.executeQuery();

			while (rs.next()) { // rs.next is boolean 1/0

				orderDetail = new OrderDetails();

				orderDetail.setOrderId(orderId);
				orderDetail.setBookid(rs.getInt("book_id"));
				orderDetail.setBookTitle(rs.getString("name"));

				orderDetails.add(orderDetail);
			}
			return orderDetails;

		} catch (Exception e) {

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

	public void insert(Order order, List<OrderDetails> orderDetailsList) {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int orderId = 0;

		try {

			conn = DBUtil.makeConnection();
			conn.setAutoCommit(false);

			String sql = "INSERT INTO `order` (student_id, submit_date) VALUES(?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, order.getStudentId());
			ps.setDate(2, new Date(System.currentTimeMillis()));

			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

			sql = "INSERT INTO `order_details` (order_id, book_id) VALUES (?,?)";

			pstm = conn.prepareStatement(sql);

			for (OrderDetails orderDetail : orderDetailsList) {

				pstm.setInt(1, orderId);
				pstm.setInt(2, orderDetail.getBookid());

				pstm.addBatch();

			}
			pstm.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

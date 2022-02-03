package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.CartServlet;

import java.sql.Date;

import db.DBUtil;
import entity.Book;
import entity.Order;
import entity.OrderDetails;

public class OrderDAO {

	public int insertOrder(Order order) throws SQLException {

		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		

			//conn = DBUtil.makeConnection();
			//conn.setAutoCommit(false);

			String sql = "INSERT INTO `order` (student_id, submit_date) value(?,?)";

			CartServlet.psOrder = CartServlet.orderConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			CartServlet.psOrder.setInt(1, order.getStudentId());
			CartServlet.psOrder.setDate(2, new Date(System.currentTimeMillis()));

			CartServlet.psOrder.execute();

			CartServlet.rsOrder = CartServlet.psOrder.getGeneratedKeys();
			if (CartServlet.rsOrder.next()) {

				return CartServlet.rsOrder.getInt(1);

			}

		return 0;

	}

	public List<Order> viewOrder(long studentId) throws SQLException {

		List<Order> orders = new ArrayList<Order>();
		Order order;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.makeConnection();

			String sql = "SELECT * FROM `order` WHERE `order`.student_id = ?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, studentId);

			rs = ps.executeQuery();

			while (rs.next()) { // rs.next is boolean 1/0

				order = new Order();

				order.setId(rs.getInt("id"));
				order.setStudentId(rs.getInt("student_id"));
				order.setSubmitDate(rs.getDate("submit_date"));

				orders.add(order);
			}

			return orders;

		} catch (Exception e) {

		} finally {

			close(conn, ps, rs);

		}

		return orders;

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

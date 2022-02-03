package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookDAO;
import dao.OrderDAO;
import dao.OrderDetailsDAO;
import db.DBUtil;
import dto.CartDTO;
import entity.Book;
import entity.Order;
import entity.OrderDetails;
import entity.Student;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REQUEST_ACTION_CASE_VIEW = "VIEW";
	private static final String REQUEST_ACTION_CASE_ADD = "ADD";
	private static final String REQUEST_ACTION_CASE_REMOVE = "REMOVE";
	private static final String REQUEST_ACTION_CASE_CHECKOUT = "CHECKOUT";
	public static Connection orderConn = null;
	public static PreparedStatement psOrder = null;
	public static ResultSet rsOrder = null;
	public static PreparedStatement psOrderDetails = null;
	// public static ResultSet rsOrderDetails = null;

	BookDAO bookDAO = new BookDAO();
	OrderDAO orderDAO = new OrderDAO();
	OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

	public CartServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String action = request.getParameter("action");

			switch (action) {

			case REQUEST_ACTION_CASE_VIEW: {

				request.getSession().setAttribute("showCart", true);

				RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
				dispatcher.forward(request, response);

				return;
			}
			case REQUEST_ACTION_CASE_ADD: { // possible to use Set instead of List

				String bookId = request.getParameter("bookId");
				Book book = bookDAO.getBookDetails(Integer.parseInt(bookId));

				CartDTO cart = (CartDTO) request.getSession().getAttribute("cart");

				boolean isExist = false;
				for (Book bookInCart : cart.getBooks()) {
					if (bookId.equals(bookInCart.getId() + "")) {
						isExist = true;
					}
				}

				if (!isExist) {
					cart.getBooks().add(book);
				}

				request.getSession().setAttribute("cart", cart);

				response.sendRedirect("HomeServlet?bookId=" + bookId);
				return;
			}

			case REQUEST_ACTION_CASE_REMOVE: {

				String bookId = request.getParameter("bookId");

				CartDTO cart = (CartDTO) request.getSession().getAttribute("cart");

				List<Book> toRemove = new ArrayList<Book>();

				for (Book bookInCart : cart.getBooks()) {
					if (bookId.equals(bookInCart.getId() + "")) {
						toRemove.add(bookInCart);
					}
				}

				cart.getBooks().removeAll(toRemove);

				request.getSession().setAttribute("cart", cart);
				response.sendRedirect("cart.jsp");
				return;

			}
			case REQUEST_ACTION_CASE_CHECKOUT: {

				Student me = (Student) request.getSession().getAttribute("me");
				//Order od = new Order();
				//.setStudentId((int) me.getId());
				
				int studentId = (int) me.getId();
				
				CartDTO cart = (CartDTO) request.getSession().getAttribute("cart");
				List<Book> books = cart.getBooks();
				
				//OrderDetails orderDetails;
				//List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
				
				//for (Book b : books) {
					
					//orderDetailsList.add(new OrderDetails(b.getId()));
					
				//}
				
				orderDetailsDAO.insertOrder(studentId, books);
				
				cart.setBooks(new ArrayList<Book>());
				request.getSession().setAttribute("cart",cart);
				response.sendRedirect("HomeServlet");
				return;
				
//				orderConn = DBUtil.makeConnection();
//				orderConn.setAutoCommit(false);
//				Savepoint savepoint = orderConn.setSavepoint();
//				
//				try {
//					
//					Order order = new Order();
//
//					order.setStudentId((int) me.getId());
//
//					int orderId = orderDAO.insertOrder(order);
//
//					CartDTO cart = (CartDTO) request.getSession().getAttribute("cart");
//
//					List<Book> books = cart.getBooks();
//
//					orderDetailsDAO.insertOrderDetails(orderId, books);
//
//					orderConn.commit();
//					orderConn.setAutoCommit(true);
//
//					cart.setBooks(new ArrayList<Book>());
//					request.getSession().setAttribute("cart", cart);
//
//					request.getSession().setAttribute("me", me);
//					response.sendRedirect("HomeServlet");
//					return;
//				} catch (SQLException e) {
//
//					orderConn.rollback(savepoint);
//					System.out.println("DB is rolled back to savepoint");
//					request.getSession().setAttribute("me", me);
//					response.sendRedirect("HomeServlet");
//
//				} finally {
//
//					if (orderConn != null) {
//						orderConn.close();
//					}
//
//					if (psOrder != null) {
//						psOrder.close();
//					}
//
//					if (rsOrder != null) {
//						rsOrder.close();
//					}
//					if (psOrderDetails != null) {
//						psOrderDetails.close();
//					}
//
//				}
			}
			}

		} catch (NumberFormatException e) {

			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDAO;
import dao.OrderDetailsDAO;
import entity.Book;
import entity.Order;
import entity.OrderDetails;
import entity.Student;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Book> books = new ArrayList<Book>();
	List<Order> orders = new ArrayList<Order>();
	List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
	OrderDAO orderDAO = new OrderDAO();
	OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

	public OrderServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Student me = (Student) request.getSession().getAttribute("me");

		String orderId = request.getParameter("orderId");
		if (orderId != null) {
			try {
				orderDetails = orderDetailsDAO.viewOrderDetails(Integer.parseInt(orderId));

				request.setAttribute("orderDetails", orderDetails);

				RequestDispatcher dispacher = request.getRequestDispatcher("order-history.jsp");
				dispacher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			try {
				orders = orderDAO.viewOrder(me.getId());

				request.setAttribute("orders", orders);

				RequestDispatcher dispacher = request.getRequestDispatcher("order-history.jsp");
				dispacher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

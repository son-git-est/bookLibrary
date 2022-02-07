package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.ha.BestResponseTimeBalanceStrategy;

import constant.Constant;
import dao.BookDAO;
import entity.Book;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDAO bookDAO = new BookDAO();
	private final static String ACTION_DELETE_BOOK = "delete_book";
	private final static String ACTION_VIEW_BOOK = "view_book";

	public BookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("bookId");
		String action = request.getParameter("action");
		try {
			switch (action) {

			case ACTION_DELETE_BOOK: {

				if (bookId != null) {

					bookDAO.deleteBook(Integer.parseInt(bookId));
					response.sendRedirect("BookBOServlet");

				}
				break;

			}

			case ACTION_VIEW_BOOK: {

				if (bookId != null) {

					Book book = bookDAO.getBookDetails(Integer.parseInt(bookId));

					request.setAttribute("book", book);
					RequestDispatcher dispacher = request.getRequestDispatcher("book-update.jsp");
					dispacher.forward(request, response);

				}

				break;
			}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("BookBOServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constant.Constant;
import dao.BookDAO;
import dao.CategoryDAO;
import entity.Book;
import entity.Category;

@WebServlet("/BookBOServlet")
public class BookBOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryDAO categoryDAO = new CategoryDAO();
	BookDAO bookDAO = new BookDAO();


	public BookBOServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		String categoryId = request.getParameter("categoryId");
		

			if (categoryId == "") {
				categoryId = null;
			}

			String currentPage = request.getParameter("currentPage");

			if (currentPage != null) {

				Constant.CURRENT_PAGE = Integer.parseUnsignedInt(currentPage);

			} else {
				Constant.CURRENT_PAGE = 1;
			}

			try {

				List<Category> categories = categoryDAO.getAllShowedCategories();
				request.setAttribute("categories", categories);

				if (bookId != null) {

					Book book = bookDAO.getBookDetails(Integer.parseInt(bookId));
					request.setAttribute("book", book);

				} else if (categoryId != null) {

					Constant.TOTAL_BOOKS = bookDAO.getTotalBooksByCategory(Integer.parseInt(categoryId));

					if (Constant.TOTAL_BOOKS % Constant.BOOK_PER_PAGE == 0) {

						Constant.TOTAL_PAGES = Constant.TOTAL_BOOKS / Constant.BOOK_PER_PAGE;

					} else {
						Constant.TOTAL_PAGES = Constant.TOTAL_BOOKS / Constant.BOOK_PER_PAGE + 1;
					}
					System.out.println(Constant.TOTAL_BOOKS);
					System.out.println(Constant.CURRENT_PAGE);
					System.out.println(Constant.TOTAL_PAGES);

					List<Book> books = bookDAO.getBooksByCategory(Integer.parseInt(categoryId), Constant.CURRENT_PAGE);
					request.setAttribute("books", books);
					request.setAttribute("currentPage", Constant.CURRENT_PAGE);
					request.setAttribute("totalPages", Constant.TOTAL_PAGES);
					request.setAttribute("categoryId", categoryId);

				} else {

					Constant.TOTAL_BOOKS = bookDAO.getTotalBooks();

					if (Constant.TOTAL_BOOKS % Constant.BOOK_PER_PAGE == 0) {

						Constant.TOTAL_PAGES = Constant.TOTAL_BOOKS / Constant.BOOK_PER_PAGE;

					} else {
						Constant.TOTAL_PAGES = Constant.TOTAL_BOOKS / Constant.BOOK_PER_PAGE + 1;
					}

					List<Book> books = bookDAO.getAllBooks(Constant.CURRENT_PAGE);
					request.setAttribute("books", books);
					request.setAttribute("currentPage", Constant.CURRENT_PAGE);
					request.setAttribute("totalPages", Constant.TOTAL_PAGES);

				}

				RequestDispatcher dispacher = request.getRequestDispatcher("book-list.jsp");
				dispacher.forward(request, response);

			} catch (

			Exception e) {

				e.printStackTrace();

			}
		}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

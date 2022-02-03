package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import entity.User;

@WebServlet("/LoginBOServlet")
public class LoginBOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = new UserDAO();

	public LoginBOServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			User user = userDAO.getUserByUsernameAndPassword(username, password);

			if (user != null) {
				request.getSession().setAttribute("user", user);
				response.sendRedirect("StudentBOServlet");
			} else {
				response.sendRedirect("loginBO-fail.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

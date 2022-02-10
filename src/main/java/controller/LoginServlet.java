package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StudentDAO;
import dto.CartDTO;
import entity.Student;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDAO studentDAO = new StudentDAO();

	public LoginServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String command = request.getParameter("command");

		if (command == null) {

			response.sendRedirect("HomeServlet");

		} else {
			switch (command) {
			case ("logout"): {
				session.removeAttribute("me");
				response.sendRedirect("HomeServlet");
				break;
			}
			default: {
				response.sendRedirect("login.jsp");
				break;
			}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		
		

		try {

			Student student = studentDAO.getStudentByEmailAndPassword(email, password);
			//Student student = studentDAO.getStudentByEmailAndHashPassword(username, password);

			if (student != null) {

				HttpSession session = request.getSession();
				session.setAttribute("me", student);
				session.setAttribute("cart", new CartDTO(new ArrayList<>()));
				/*
				 * session.setAttribute("frst", student.getFirstName());
				 * session.setAttribute("last", student.getLastName());
				 */
				response.sendRedirect("HomeServlet");
			} else {
				response.sendRedirect("login.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

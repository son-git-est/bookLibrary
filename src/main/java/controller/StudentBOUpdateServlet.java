package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDAO;

/**
 * Servlet implementation class StudentBOUpdateServlet
 */
@WebServlet("/StudentBOUpdateServlet")
public class StudentBOUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentBOUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("studentId");
		int id = Integer.parseInt(studentId);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		StudentDAO studentDAO = new StudentDAO();

		try {

			boolean rs = studentDAO.updateStudentDetailsById(id, firstName, lastName, email);

			if (rs == true) {
				response.sendRedirect("StudentBOServlet");
			}

			else {

				response.sendRedirect("StudentBOServlet");

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}

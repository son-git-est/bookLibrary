package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDAO;
import entity.Student;

@WebServlet("/StudentBOServlet")
public class StudentBOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPDATE_STUDENT_DETAILS = "update";
	private static final String RESTORE_STUDENT_PASSWORD = "restore";
	private static final String DELETE_STUDENT = "delete";

	public StudentBOServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students = new ArrayList<Student>();

		StudentDAO studentDAO = new StudentDAO();

		String action = request.getParameter("action");
		try {
			if (action != null) {

				switch (action) {

				case RESTORE_STUDENT_PASSWORD: {
					String studentId = request.getParameter("studentId");
					studentDAO.setStudentPasswordToDefault(Integer.parseInt(studentId));
					students = studentDAO.getStudentList();
					request.getSession().setAttribute("students", students);
					response.sendRedirect("student-list.jsp");
					return;

				}

				case UPDATE_STUDENT_DETAILS: {
					String studentId = request.getParameter("studentId");

					Student student = studentDAO.getStudentDetails(studentId);
					request.getSession().setAttribute("student", student);
					response.sendRedirect("student-list.jsp");
					return;

				}
				case DELETE_STUDENT: {
					String studentId = request.getParameter("studentId");
					studentDAO.deleteStudentById(Integer.parseInt(studentId));
					students = studentDAO.getStudentList();
					request.getSession().setAttribute("students", students);
					response.sendRedirect("student-list.jsp");
					return;
				}

				}

			} else {

				students = studentDAO.getStudentList();
				request.getSession().setAttribute("students", students);
				response.sendRedirect("student-list.jsp");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		StudentDAO studentDAO = new StudentDAO();

		try {

			boolean rs = studentDAO.addNewStudent(firstName, lastName, email);

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

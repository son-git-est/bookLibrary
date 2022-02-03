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

	public StudentBOServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students = new ArrayList<Student>();

		StudentDAO studentDAO = new StudentDAO();

		try {
			students = studentDAO.getStudentList();
			request.getSession().setAttribute("students", students);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		response.sendRedirect("student-list.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

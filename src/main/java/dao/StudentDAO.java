package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constant.Constant;
import constant.SecureHash;
import db.DBUtil;
import entity.Student;

public class StudentDAO {
	public Student getStudentByEmailAndPassword1(String email, String password) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Student student = null;

		try {
			// make connection to mysql
			conn = DBUtil.makeConnection();

			System.out.println("conn " + conn);
			String sql = "SELECT * FROM student WHERE email = ? AND password = ?";

			// ps contains sql and parameter values
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			System.out.println("ps:" + ps);
			// rs receives results from mysql
			rs = ps.executeQuery();
			System.out.println("rs:" + rs);
			// rs -> record or many records
			// rs.next will return boolean if data exists for the first results we will get
			// them and put into Student entity
			if (rs.next()) { // rs.next is boolean 1/0
				long id = rs.getLong("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				student = new Student(id, firstName, lastName, email, password);

			} else {
				return null;
			}

			return student;

		} catch (Exception e) {

			e.printStackTrace();

		} finally { // must be closed after all queries finish to prevent overwhelming server
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}

		}

		return null;

	}

	public Student getStudentByEmailAndPassword(String email, String password) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Student student = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student WHERE email = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) { // rs.next is boolean 1/0
				long id = rs.getLong("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String storedPassword = rs.getString("password");
				String salt = rs.getString("salt");

				boolean validation = SecureHash.validateSHA265Password(password, storedPassword, salt);

				if (validation) {
					student = new Student(id, firstName, lastName, email, storedPassword);
					return student;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public void changeStudentPassword(String email, String oldPassword, String newPassword) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student student = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student WHERE email = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) { // rs.next is boolean 1/0
				long id = rs.getLong("id");
				String storedPassword = rs.getString("password");
				String salt = rs.getString("salt");

				boolean validation = SecureHash.validateSHA265Password(oldPassword, storedPassword, salt);

				if (validation) {

					String password = SecureHash.getPBKDF2Password(newPassword, salt);

					sql = "UPDATE student SET password = ? where (id = ?)";
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, password);
					pstm.setLong(2, id);

					pstm.executeUpdate();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public boolean addNewStudent(String firstName, String lastName, String email) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "INSERT IGNORE INTO student (first_name, last_name, email, password, salt) VALUES (?,?,?, ?, ?)";

			String salt = SecureHash.getSalt();
			String defaultPassword = Constant.DEFAULT_STUDENT_PASSWORD;
			String password = SecureHash.getPBKDF2Password(defaultPassword, salt);

			ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(4, salt);

			int result = ps.executeUpdate();

			if (result == 1) {

				return true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return false;

	}

	public List<Student> getStudentList() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Student> studentList = new ArrayList<Student>();
		Student student = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) { // rs.next is boolean 1/0
				long id = rs.getLong("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");

				student = new Student(id, firstName, lastName, email, password);
				studentList.add(student);
			}
			return studentList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // must be closed after all queries finish to prevent overwhelming server
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

}

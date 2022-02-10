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

			conn = DBUtil.makeConnection();

			System.out.println("conn " + conn);
			String sql = "SELECT * FROM student WHERE email = ? AND password = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			System.out.println("ps:" + ps);

			rs = ps.executeQuery();

			if (rs.next()) {
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

	public boolean updateStudentDetailsById(int studentId, String firstName, String lastName, String email)
			throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "UPDATE student SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setLong(4, studentId);

			int rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return false;
	}

	public boolean setStudentPasswordToDefault(int studentId) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student WHERE id = ?";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, studentId);
			rs = ps.executeQuery();

			if (rs.next()) {
				long id = rs.getLong("id");
				String salt = rs.getString("salt");
				String defaultPassword = Constant.DEFAULT_STUDENT_PASSWORD;
				String password = SecureHash.getPBKDF2Password(defaultPassword, salt);
				
				System.out.println(defaultPassword);
				

				sql = "UPDATE student SET password = ? where id = ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, password);
				pstm.setLong(2, id);

				pstm.executeUpdate();
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

		return false;
	}

	public boolean deleteStudentById(int studentId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "DELETE FROM student WHERE id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			int rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return false;
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

			if (rs.next()) {
				long id = rs.getLong("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String storedPassword = rs.getString("password");
				String salt = rs.getString("salt");

				boolean validation = SecureHash.validatePBKDF2Password(password, storedPassword, salt);

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

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student WHERE email = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) {
				long id = rs.getLong("id");
				String storedPassword = rs.getString("password");
				String salt = rs.getString("salt");

				boolean validation = SecureHash.validateSHA265Password(oldPassword, storedPassword, salt);

				if (validation) {

					String password = SecureHash.getPBKDF2Password(newPassword, salt);

					sql = "UPDATE student SET password = ? where id = ?";
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
			String sql = "INSERT IGNORE INTO coding_mentor.student (first_name, last_name, email, password, salt) VALUES (?,?,?, ?, ?)";

			String salt = SecureHash.getSalt();
			String defaultPassword = Constant.DEFAULT_STUDENT_PASSWORD;
			String password = SecureHash.getPBKDF2Password(defaultPassword, salt);

			ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, salt);

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

			while (rs.next()) {
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

	public Student getStudentDetails(String studentId) throws SQLException {
		Student student = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "SELECT * FROM student WHERE id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(studentId));
			rs = ps.executeQuery();

			if (rs.next()) {
				long id = rs.getLong("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");

				student = new Student(id, firstName, lastName, email, password);
				return student;
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

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import constant.SecureHash;
import db.DBUtil;
import entity.User;

public class UserDAO {
//	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
//		User user = null;
//
//		Connection conn = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//
//		try {
//			conn = DBUtil.makeConnection();
//
//			// create sql for insert
//			String sql = "SELECT * FROM user WHERE user_name = ? AND password = ?";
//
//			pstm = conn.prepareStatement(sql);
//
//			// set params
//			pstm.setString(1, username);
//			pstm.setString(2, password);
//
//			rs = pstm.executeQuery();
//
//			if (rs.next()) {
//				String email = rs.getString("email");
//				int id = rs.getInt("id");
//
//				user = new User(id, username, password, email);
//			} else {
//				return null;
//			}
//			return user;
//		} finally {
//			close(conn, pstm, rs);
//		}
//	}

	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		User user = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();

			String sql = "SELECT * FROM user WHERE user_name = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			rs = pstm.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String storedPassword = rs.getString("password");
				String salt = rs.getString("salt");

				boolean validation = SecureHash.validateSHA265Password(password, storedPassword, salt);
				storedPassword = "***";

				if (validation) {
					user = new User(id, username, storedPassword, email);
					return user;
				}
			} else {
				return null;
			}
			return null;
		} finally {
			close(conn, pstm, rs);
		}
	}

	public User createNewUserWithUsernamePasswordEmail(String username, String password, String email)
			throws SQLException {
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();
			conn.setAutoCommit(false);
			String salt = SecureHash.getSalt();
			String hashPassword = SecureHash.getSHA256Password(password, salt);
			String sql = "INSERT INTO `user` (`user_name`, `password`, `email`, `salt`) VALUES(?,?,?,?)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.setString(2, hashPassword);
			ps.setString(3, email);
			ps.setString(4, salt);
			ps.execute();

			conn.commit();
			conn.setAutoCommit(true);

			rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);

			user = new User(id, username, hashPassword, email);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			close(conn, ps, rs);
		}
		return user;
	}

	private void close(Connection conn, Statement stm, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stm != null) {
				stm.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import entity.Category;

public class CategoryDAO {
	public List<Category> getAllShowedCategories() throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Category category = null;
		List<Category> categories = new ArrayList<Category>();

		try {
			// make connection to mysql
			conn = DBUtil.makeConnection();

			System.out.println("conn " + conn);
			String sql = "SELECT * FROM category where `show` = 1";

			// ps contains sql and parameter values
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			// rs -> record or many records
			// rs.next will return boolean if data exists for the first results we will get
			// them and put into Student entity
			while (rs.next()) { // rs.next is boolean 1/0

				category = new Category();

				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setShow(rs.getBoolean("show"));

				categories.add(category);

			}
			return categories;

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

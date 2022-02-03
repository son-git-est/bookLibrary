package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static Connection makeConnection() {
		
		try {
			

			Class.forName("com.mysql.cj.jdbc.Driver");
			//final String url = "jdbc:mysql:///coding_mentor";
			final String url = "jdbc:mysql://localhost:3306/coding_mentor";
		    final String user = "admin";
		    final String password = "admin";
		    
		    Connection conn = DriverManager.getConnection(url, user, password);
		    //System.out.println("conn is" + conn);
			return conn;
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coding_mentor?serverTimezone=UTC", "admin", "admin");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}

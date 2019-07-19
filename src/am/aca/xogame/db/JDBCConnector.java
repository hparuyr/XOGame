package am.aca.xogame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {
	// JDBC driver name and database URL
	static final String DB_URL = "jdbc:mysql://localhost:3306/XOGame";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Rootroot";

	private static JDBCConnector instance;
	Connection conn = null;

	private JDBCConnector() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	public static JDBCConnector getInstace() throws ClassNotFoundException {
		if (instance == null) {
			instance = new JDBCConnector();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
}

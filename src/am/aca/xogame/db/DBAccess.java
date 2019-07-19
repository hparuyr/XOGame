package am.aca.xogame.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import am.aca.xogame.beans.GameBean;

public class DBAccess {
	private static final String createGameSQL = "INSERT INTO GAME(PLAYER1) VALUES(?)";
	private static final String createJoinSQL = "UPDATE GAME SET PLAYER2=? WHERE ID=?";
	private static final String getAvailableGame = "SELECT * FROM GAME where PLAYER2 is null limit 1";

	public static Integer createGame(String player1) {
		Connection con = null;
		PreparedStatement state = null;
		try {
			con = JDBCConnector.getInstace().getConnection();
			
			//Pay attention that we ask to return auto incremented key
			state = con.prepareStatement(createGameSQL, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, player1);
			state.executeUpdate();
			
			// To get just created Game Id
	        try (ResultSet generatedKeys = state.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static Integer joinGame(Integer gameId, String player2) {
		Connection con = null;
		PreparedStatement state = null;
		try {
			con = JDBCConnector.getInstace().getConnection();
			state = con.prepareStatement(createJoinSQL);
			state.setString(1, player2);
			state.setInt(2, gameId);
			return state.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static GameBean getAvailableGames() {
		GameBean result = null;
		Connection con = null;
		PreparedStatement state = null;
		try {
			con = JDBCConnector.getInstace().getConnection();
			state = con.prepareStatement(getAvailableGame);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				result = new GameBean();
				result.setId(rs.getInt("ID"));
				result.setPlayer1(rs.getString("PLAYER1"));
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				state.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}

package app.comfyhub.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.comfyhub.modelo.User;

public class UserDAO {

	public User save(final User user) throws DataAccessException {
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO users (username, email, password) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		return user;
	}

	public User findByUsername(String username) throws DataAccessException {
		User user = null;
		
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, username, email, password FROM users WHERE username = ?");
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		return user;
	}

	public User findByEmail(String email) throws DataAccessException {
		User user = null;
		
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, username, email, password FROM users WHERE email = ?");
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		return user;
	}
}

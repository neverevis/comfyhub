package app.comfyhub.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.comfyhub.modelo.Post;

public class PostDAO {

	public Post save(final Post post) throws DataAccessException {
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO posts (user_id, text, image_path) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, post.getUserId());
			ps.setString(2, post.getText());
			ps.setString(3, post.getImagePath());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				post.setId(rs.getInt(1));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		return post;
	}

	public List<Post> findInitial(int limit) throws DataAccessException {
		List<Post> posts = new ArrayList<>();
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT p.id, p.user_id, u.username, p.text, p.image_path, p.created_at " +
					"FROM posts p " +
					"JOIN users u ON p.user_id = u.id " +
					"ORDER BY p.id DESC " +
					"LIMIT ?");
			ps.setInt(1, limit);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setUserId(rs.getInt("user_id"));
				post.setUsername(rs.getString("username"));
				post.setText(rs.getString("text"));
				post.setImagePath(rs.getString("image_path"));
				post.setCreatedAt(rs.getTimestamp("created_at"));
				posts.add(post);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return posts;
	}

	public List<Post> findOlderThan(int cursorId, int limit) throws DataAccessException {
		List<Post> posts = new ArrayList<>();
		try {
			Connection conn = DatabaseConnector.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT p.id, p.user_id, u.username, p.text, p.image_path, p.created_at " +
					"FROM posts p " +
					"JOIN users u ON p.user_id = u.id " +
					"WHERE p.id < ? " +
					"ORDER BY p.id DESC " +
					"LIMIT ?");
			ps.setInt(1, cursorId);
			ps.setInt(2, limit);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setUserId(rs.getInt("user_id"));
				post.setUsername(rs.getString("username"));
				post.setText(rs.getString("text"));
				post.setImagePath(rs.getString("image_path"));
				post.setCreatedAt(rs.getTimestamp("created_at"));
				posts.add(post);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return posts;
	}
}

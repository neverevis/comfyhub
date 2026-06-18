package app.comfyhub.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import app.comfyhub.controllers.post.PostDTO;
import app.comfyhub.modelo.Post;
import app.comfyhub.persistence.PostDAO;

public class PostService {
	private PostDAO dao = new PostDAO();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public void createPost(String text, String imagePath, int userId) throws IllegalArgumentException {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("O texto do post é obrigatório.");
		}

		Post post = new Post();
		post.setUserId(userId);
		post.setText(text.trim());
		post.setImagePath(imagePath);

		dao.save(post);
	}

	public List<PostDTO> getInitialFeed(int limit) {
		List<Post> list = dao.findInitial(limit);
		return mapToDTOList(list);
	}

	public List<PostDTO> getOlderPosts(int cursorId, int limit) {
		List<Post> list = dao.findOlderThan(cursorId, limit);
		return mapToDTOList(list);
	}

	private List<PostDTO> mapToDTOList(List<Post> list) {
		List<PostDTO> dtos = new ArrayList<>();
		for (Post p : list) {
			String formattedDate = p.getCreatedAt() != null ? sdf.format(p.getCreatedAt()) : "";
			dtos.add(new PostDTO(
				p.getId(),
				p.getUserId(),
				p.getUsername(),
				p.getText(),
				p.getImagePath(),
				formattedDate
			));
		}
		return dtos;
	}
}

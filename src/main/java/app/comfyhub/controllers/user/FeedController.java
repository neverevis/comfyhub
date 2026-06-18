package app.comfyhub.controllers.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import app.comfyhub.controllers.post.PostDTO;
import app.comfyhub.service.PostService;
import app.comfyhub.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/feed")
public class FeedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostService postService = new PostService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado");

		if (usuarioLogado == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return;
		}

		String ajax = request.getParameter("ajax");
		if ("true".equals(ajax)) {
			String cursorStr = request.getParameter("cursor");
			int cursorId = 0;
			if (cursorStr != null && !cursorStr.isBlank()) {
				cursorId = Integer.parseInt(cursorStr);
			}

			List<PostDTO> olderPosts = postService.getOlderPosts(cursorId, 5);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			for (PostDTO post : olderPosts) {
				out.println("<div class=\"post-item\" data-id=\"" + post.id() + "\">");
				out.println("<p><strong>" + post.username() + "</strong> - " + post.createdAt() + "</p>");
				out.println("<p>" + post.text() + "</p>");
				if (post.imagePath() != null) {
					out.println("<img src=\"" + request.getContextPath() + "/uploads/" + post.imagePath() + "\" style=\"max-width:300px; display:block;\">");
				}
				out.println("<hr>");
				out.println("</div>");
			}
			return;
		}

		List<PostDTO> initialPosts = postService.getInitialFeed(5);
		request.setAttribute("posts", initialPosts);
		ViewHelper.forward("feed.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

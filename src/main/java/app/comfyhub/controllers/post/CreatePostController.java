package app.comfyhub.controllers.post;

import java.io.File;
import java.io.IOException;

import app.comfyhub.controllers.user.UserDTO;
import app.comfyhub.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/post/create")
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	maxFileSize = 1024 * 1024 * 10,      // 10MB
	maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class CreatePostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostService postService = new PostService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado");

		if (usuarioLogado == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return;
		}

		String text = request.getParameter("text");
		Part part = request.getPart("image");
		
		String imagePath = null;
		if (part != null && part.getSize() > 0) {
			String submittedFileName = part.getSubmittedFileName();
			if (submittedFileName != null && !submittedFileName.isBlank()) {
				String cleanFileName = System.currentTimeMillis() + "_" + submittedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
				String uploadPath = getServletContext().getRealPath("/uploads");
				
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				
				part.write(uploadPath + File.separator + cleanFileName);
				imagePath = cleanFileName;
			}
		}

		try {
			postService.createPost(text, imagePath, usuarioLogado.id());
			response.sendRedirect(request.getContextPath() + "/feed");
		} catch (IllegalArgumentException e) {
			request.setAttribute("erro", e.getMessage());
			request.getRequestDispatcher("/feed").forward(request, response);
		}
	}
}

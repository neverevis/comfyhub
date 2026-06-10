package app.comfyhub.controllers.user;

import java.io.IOException;

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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado");

		if (usuarioLogado == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
		} else {
			ViewHelper.forward("feed.jsp", request, response);
		}
	}
}

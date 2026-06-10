package app.comfyhub.controllers.user;

import java.io.IOException;

import app.comfyhub.service.UserService;
import app.comfyhub.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/login")
public class LoginUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("form", new LoginUserForm("", ""));
		ViewHelper.forward("user/login.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		LoginUserForm form = new LoginUserForm(username, password);
		request.setAttribute("form", form);

		try {
			UserDTO userDTO = userService.authenticate(form);
			
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogado", userDTO);

			response.sendRedirect(request.getContextPath() + "/feed");
		} catch (IllegalArgumentException e) {
			request.setAttribute("erro", e.getMessage());
			ViewHelper.forward("user/login.jsp", request, response);
		}
	}
}

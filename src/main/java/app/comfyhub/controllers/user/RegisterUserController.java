package app.comfyhub.controllers.user;

import java.io.IOException;

import app.comfyhub.service.UserService;
import app.comfyhub.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/register")
public class RegisterUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("form", new RegisterUserForm("", "", ""));
		ViewHelper.forward("user/register.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		RegisterUserForm form = new RegisterUserForm(username, email, password);
		request.setAttribute("form", form);

		try {
			userService.register(form);
			response.sendRedirect(request.getContextPath() + "/user/login");
		} catch (IllegalArgumentException e) {
			request.setAttribute("erro", e.getMessage());
			ViewHelper.forward("user/register.jsp", request, response);
		}
	}
}

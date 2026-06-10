package app.comfyhub.service;

import app.comfyhub.controllers.user.RegisterUserForm;
import app.comfyhub.controllers.user.LoginUserForm;
import app.comfyhub.controllers.user.UserDTO;
import app.comfyhub.modelo.User;
import app.comfyhub.persistence.UserDAO;

public class UserService {
	private UserDAO dao = new UserDAO();

	public void register(RegisterUserForm form) throws IllegalArgumentException {
		if (form.username() == null || form.username().isBlank()) {
			throw new IllegalArgumentException("O nome de usuário é obrigatório.");
		}
		if (form.email() == null || form.email().isBlank()) {
			throw new IllegalArgumentException("O e-mail é obrigatório.");
		}
		if (form.password() == null || form.password().isBlank()) {
			throw new IllegalArgumentException("A senha é obrigatória.");
		}

		User userExistente = dao.findByUsername(form.username());
		if (userExistente != null) {
			throw new IllegalArgumentException("Nome de usuário já está em uso.");
		}

		userExistente = dao.findByEmail(form.email());
		if (userExistente != null) {
			throw new IllegalArgumentException("E-mail já está em uso.");
		}

		User novoUser = new User();
		novoUser.setUsername(form.username().trim());
		novoUser.setEmail(form.email().trim());
		novoUser.setPassword(form.password());

		dao.save(novoUser);
	}

	public UserDTO authenticate(LoginUserForm form) throws IllegalArgumentException {
		if (form.username() == null || form.username().isBlank()) {
			throw new IllegalArgumentException("Digite o nome de usuário.");
		}
		if (form.password() == null || form.password().isBlank()) {
			throw new IllegalArgumentException("Digite a sua senha.");
		}

		User user = dao.findByUsername(form.username().trim());

		if (user == null || !user.getPassword().equals(form.password())) {
			throw new IllegalArgumentException("Usuário ou senha incorretos.");
		}

		return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
	}
}

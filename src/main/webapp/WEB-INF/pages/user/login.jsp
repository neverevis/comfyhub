<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.LoginUserForm" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ComfyHub - Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="wrapper">
        <div class="card">
            <h2>Entrar no ComfyHub</h2>

            <%
            LoginUserForm form = (LoginUserForm) request.getAttribute("form");
            String erro = (String) request.getAttribute("erro");
            if (erro != null) {
            %>
                <div class="alert-error" style="margin-bottom: 16px;"><%= erro %></div>
            <% } %>

            <form method="post" action="<%= request.getContextPath() %>/user/login">
                <div class="form-group">
                    <label for="username">Nome de Usuário:</label>
                    <input type="text" name="username" id="username" value="<%= form != null ? form.username() : "" %>" required placeholder="Seu nome de usuário">
                </div>

                <div class="form-group">
                    <label for="password">Senha:</label>
                    <input type="password" name="password" id="password" required placeholder="Sua senha">
                </div>

                <button type="submit" style="margin-top: 8px;">Entrar</button>
            </form>

            <hr style="margin: 24px 0; border: 0; border-top: 1px solid var(--border-color);">

            <p style="text-align: center; font-size: 0.9rem;">Não tem uma conta? <a href="<%= request.getContextPath() %>/user/register" class="btn-link">Cadastrar-se</a></p>
            <p style="text-align: center; margin-top: 8px; font-size: 0.9rem;"><a href="<%= request.getContextPath() %>/index.html" class="btn-link" style="color: var(--text-muted);">Voltar para o início</a></p>
        </div>
    </div>
</body>
</html>

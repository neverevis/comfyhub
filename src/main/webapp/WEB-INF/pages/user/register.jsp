<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.RegisterUserForm" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ComfyHub - Cadastro</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="wrapper">
        <div class="card">
            <h2>Criar Conta</h2>

            <%
            RegisterUserForm form = (RegisterUserForm) request.getAttribute("form");
            String erro = (String) request.getAttribute("erro");
            if (erro != null) {
            %>
                <div class="alert-error" style="margin-bottom: 16px;"><%= erro %></div>
            <% } %>

            <form method="post" action="<%= request.getContextPath() %>/user/register">
                <div class="form-group">
                    <label for="username">Nome de Usuário:</label>
                    <input type="text" name="username" id="username" value="<%= form != null ? form.username() : "" %>" required placeholder="Escolha um nome de usuário">
                </div>

                <div class="form-group">
                    <label for="email">E-mail:</label>
                    <input type="email" name="email" id="email" value="<%= form != null ? form.email() : "" %>" required placeholder="Digite seu melhor e-mail">
                </div>

                <div class="form-group">
                    <label for="password">Senha:</label>
                    <input type="password" name="password" id="password" required placeholder="Crie uma senha segura">
                </div>

                <button type="submit" style="margin-top: 8px;">Cadastrar</button>
            </form>

            <hr style="margin: 24px 0; border: 0; border-top: 1px solid var(--border-color);">

            <p style="text-align: center; font-size: 0.9rem;">Já tem uma conta? <a href="<%= request.getContextPath() %>/user/login" class="btn-link">Entrar</a></p>
            <p style="text-align: center; margin-top: 8px; font-size: 0.9rem;"><a href="<%= request.getContextPath() %>/index.html" class="btn-link" style="color: var(--text-muted);">Voltar para o início</a></p>
        </div>
    </div>
</body>
</html>

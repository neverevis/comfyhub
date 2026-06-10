<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.UserDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ComfyHub - Feed</title>
</head>
<body>
    <%
    UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado");
    %>

    <h1>ComfyHub - Feed de Notícias</h1>

    <% if (usuarioLogado != null) { %>
        <p>Login realizado com sucesso! Bem-vindo, <strong><%= usuarioLogado.username() %></strong>.</p>
        <p>E-mail cadastrado: <%= usuarioLogado.email() %></p>
        <br>
        <p><a href="<%= request.getContextPath() %>/user/logout">Sair da Conta (Logout)</a></p>
    <% } else { %>
        <p>Você não está autenticado. <a href="<%= request.getContextPath() %>/user/login">Clique aqui para entrar</a>.</p>
    <% } %>
</body>
</html>

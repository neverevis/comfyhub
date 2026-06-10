<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.LoginUserForm" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ComfyHub - Login</title>
</head>
<body>
    <%
    LoginUserForm form = (LoginUserForm) request.getAttribute("form");
    String erro = (String) request.getAttribute("erro");
    %>

    <h1>Entrar no ComfyHub</h1>

    <% if (erro != null) { %>
        <p style="color: red;"><%= erro %></p>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/user/login">
        <label for="username">Nome de Usuário:</label>
        <input type="text" name="username" id="username" value="<%= form != null ? form.username() : "" %>">
        <br><br>

        <label for="password">Senha:</label>
        <input type="password" name="password" id="password">
        <br><br>

        <button type="submit">Entrar</button>
    </form>

    <br>
    <p>Não tem uma conta? <a href="<%= request.getContextPath() %>/user/register">Cadastrar-se</a></p>
    <p><a href="<%= request.getContextPath() %>/index.html">Voltar para o início</a></p>
</body>
</html>

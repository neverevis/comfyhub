<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.RegisterUserForm" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ComfyHub - Cadastro</title>
</head>
<body>
    <%
    RegisterUserForm form = (RegisterUserForm) request.getAttribute("form");
    String erro = (String) request.getAttribute("erro");
    %>

    <h1>Cadastrar no ComfyHub</h1>

    <% if (erro != null) { %>
        <p style="color: red;"><%= erro %></p>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/user/register">
        <label for="username">Nome de Usuário:</label>
        <input type="text" name="username" id="username" value="<%= form != null ? form.username() : "" %>">
        <br><br>

        <label for="email">E-mail:</label>
        <input type="email" name="email" id="email" value="<%= form != null ? form.email() : "" %>">
        <br><br>

        <label for="password">Senha:</label>
        <input type="password" name="password" id="password">
        <br><br>

        <button type="submit">Cadastrar</button>
    </form>

    <br>
    <p>Já tem uma conta? <a href="<%= request.getContextPath() %>/user/login">Entrar</a></p>
    <p><a href="<%= request.getContextPath() %>/index.html">Voltar para o início</a></p>
</body>
</html>

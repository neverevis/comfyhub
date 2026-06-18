<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.comfyhub.controllers.user.UserDTO" %>
<%@ page import="app.comfyhub.controllers.post.PostDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ComfyHub - Feed</title>
</head>
<body>
    <%
    UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado");
    List<PostDTO> posts = (List<PostDTO>) request.getAttribute("posts");
    String erro = (String) request.getAttribute("erro");
    %>

    <h1>ComfyHub - Feed de Notícias</h1>

    <% if (usuarioLogado != null) { %>
        <p>Logado como: <strong><%= usuarioLogado.username() %></strong> | <a href="<%= request.getContextPath() %>/user/logout">Sair</a></p>
        <hr>

        <h2>Criar Nova Publicação</h2>
        <% if (erro != null) { %>
            <p style="color: red;"><%= erro %></p>
        <% } %>
        
        <form method="post" action="<%= request.getContextPath() %>/post/create" enctype="multipart/form-data">
            <label for="text">O que você está pensando?</label><br>
            <textarea name="text" id="text" rows="4" cols="50" required></textarea>
            <br><br>

            <label for="image">Adicionar Foto (opcional):</label><br>
            <input type="file" name="image" id="image" accept="image/*">
            <br><br>

            <button type="submit">Publicar</button>
        </form>

        <hr>

        <h2>Publicações Recentes</h2>
        <div id="feed-container">
            <div id="post-list">
                <% if (posts != null && !posts.isEmpty()) { %>
                    <% for (PostDTO post : posts) { %>
                        <div class="post-item" data-id="<%= post.id() %>">
                            <p><strong><%= post.username() %></strong> - <%= post.createdAt() %></p>
                            <p><%= post.text() %></p>
                            <% if (post.imagePath() != null) { %>
                                <img src="<%= request.getContextPath() %>/uploads/<%= post.imagePath() %>" style="max-width:300px; display:block;">
                            <% } %>
                            <hr>
                        </div>
                    <% } %>
                <% } else { %>
                    <p>Nenhuma publicação ainda. Seja o primeiro a postar!</p>
                <% } %>
            </div>
            
            <% if (posts != null && posts.size() >= 5) { %>
                <button id="load-more-btn" onclick="loadMore()">Carregar Mais</button>
            <% } %>
        </div>

        <script>
        function loadMore() {
            let posts = document.querySelectorAll('.post-item');
            if (posts.length === 0) return;
            
            let lastPost = posts[posts.length - 1];
            let lastId = lastPost.getAttribute('data-id');
            
            fetch('<%= request.getContextPath() %>/feed?cursor=' + lastId + '&ajax=true')
                .then(response => response.text())
                .then(html => {
                    if (html.trim() === '') {
                        document.getElementById('load-more-btn').style.display = 'none';
                        let noMoreMsg = document.createElement('p');
                        noMoreMsg.innerText = 'Não há mais posts para carregar.';
                        document.getElementById('feed-container').appendChild(noMoreMsg);
                    } else {
                        document.getElementById('post-list').insertAdjacentHTML('beforeend', html);
                    }
                })
                .catch(err => {
                    console.error('Erro ao carregar mais posts:', err);
                });
        }
        </script>

    <% } else { %>
        <p>Você não está autenticado. <a href="<%= request.getContextPath() %>/user/login">Clique aqui para entrar</a>.</p>
    <% } %>
</body>
</html>

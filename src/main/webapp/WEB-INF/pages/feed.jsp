<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="app.comfyhub.controllers.user.UserDTO" %>
<%@ page import="app.comfyhub.controllers.post.PostDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ComfyHub - Feed</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <% 
    UserDTO usuarioLogado = (UserDTO) session.getAttribute("usuarioLogado"); 
    List<PostDTO> posts = (List<PostDTO>) request.getAttribute("posts");
    String erro = (String) request.getAttribute("erro");
    %>

    <div class="wrapper">
        <% if (usuarioLogado != null) { %>
            <!-- Feed Header -->
            <div class="feed-header">
                <div>
                    <h1 style="font-size: 1.75rem; text-align: left; margin: 0;">ComfyHub</h1>
                    <p style="font-size: 0.85rem; margin: 0; color: var(--text-muted);">
                        Logado como: <strong><%= usuarioLogado.username() %></strong>
                    </p>
                </div>
                <a href="<%= request.getContextPath() %>/user/logout" class="btn-link" style="color: var(--error-color); font-weight: 500;">Sair</a>
            </div>

            <!-- Create Post Card -->
            <div class="card">
                <h2>Criar Nova Publicação</h2>
                <% if (erro != null) { %>
                    <div class="alert-error" style="margin-bottom: 16px;"><%= erro %></div>
                <% } %>

                <form method="post" action="<%= request.getContextPath() %>/post/create" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="text">O que você está pensando?</label>
                        <textarea name="text" id="text" rows="3" required placeholder="Escreva sua mensagem..."></textarea>
                    </div>

                    <div class="form-group">
                        <label for="image">Adicionar Foto (opcional):</label>
                        <input type="file" name="image" id="image" accept="image/*">
                    </div>

                    <button type="submit" style="margin-top: 8px;">Publicar</button>
                </form>
            </div>

            <!-- Feed Posts -->
            <div id="feed-container">
                <h2 style="margin-bottom: 12px;">Publicações Recentes</h2>
                <div id="post-list">
                    <% if (posts != null && !posts.isEmpty()) { %>
                        <% for (PostDTO post : posts) { %>
                            <div class="post-item" data-id="<%= post.id() %>">
                                <div class="post-meta">
                                    <strong><%= post.username() %></strong> &bull; <%= post.createdAt() %>
                                </div>
                                <div class="post-text"><%= post.text() %></div>
                                <% if (post.imagePath() != null) { %>
                                    <img src="<%= request.getContextPath() %>/uploads/<%= post.imagePath() %>" class="post-image" alt="Imagem do post">
                                <% } %>
                            </div>
                        <% } %>
                    <% } else { %>
                        <div class="no-more-msg" id="no-posts-msg">Nenhuma publicação ainda. Seja o primeiro a postar!</div>
                    <% } %>
                </div>

                <% if (posts != null && posts.size() >= 5) { %>
                    <button id="load-more-btn" onclick="loadMore()" style="margin-top: 16px; width: 100%;">Carregar Mais</button>
                <% } %>
            </div>

            <script>
                function loadMore() {
                    let posts = document.querySelectorAll('.post-item');
                    if (posts.length === 0) return;

                    let lastPost = posts[posts.length - 1];
                    let lastId = lastPost.getAttribute('data-id');
                    let btn = document.getElementById('load-more-btn');

                    btn.disabled = true;
                    btn.innerText = 'Carregando...';

                    fetch('<%= request.getContextPath() %>/feed?cursor=' + lastId + '&ajax=true')
                        .then(response => response.text())
                        .then(html => {
                            btn.disabled = false;
                            btn.innerText = 'Carregar Mais';
                            if (html.trim() === '') {
                                btn.style.display = 'none';
                                let noMoreMsg = document.createElement('div');
                                noMoreMsg.className = 'no-more-msg';
                                noMoreMsg.innerText = 'Você chegou ao fim das publicações.';
                                document.getElementById('feed-container').appendChild(noMoreMsg);
                            } else {
                                document.getElementById('post-list').insertAdjacentHTML('beforeend', html);
                                
                                // Opcional: contar se vieram menos de 5 posts
                                let tempDiv = document.createElement('div');
                                tempDiv.innerHTML = html;
                                let count = tempDiv.querySelectorAll('.post-item').length;
                                if (count < 5) {
                                    btn.style.display = 'none';
                                    let noMoreMsg = document.createElement('div');
                                    noMoreMsg.className = 'no-more-msg';
                                    noMoreMsg.innerText = 'Você chegou ao fim das publicações.';
                                    document.getElementById('feed-container').appendChild(noMoreMsg);
                                }
                            }
                        })
                        .catch(err => {
                            btn.disabled = false;
                            btn.innerText = 'Carregar Mais';
                            console.error('Erro ao carregar mais posts:', err);
                        });
                }
            </script>

        <% } else { %>
            <div class="card" style="text-align: center;">
                <h2>Acesso Restrito</h2>
                <p>Você não está autenticado no momento.</p>
                <br>
                <a href="<%= request.getContextPath() %>/user/login" class="btn">Entrar na minha conta</a>
            </div>
        <% } %>
    </div>
</body>
</html>
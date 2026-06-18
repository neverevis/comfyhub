package app.comfyhub.controllers.post;

public record PostDTO(int id, int userId, String username, String text, String imagePath, String createdAt) { }

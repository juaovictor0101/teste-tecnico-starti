package com.juaovictor0101.post_api.dto;

import com.juaovictor0101.post_api.model.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String message,
        Long userId,
        String authorUsername,
        Long postId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponseDTO fromEntity(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getMessage(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}

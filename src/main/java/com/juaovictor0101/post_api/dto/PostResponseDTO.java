package com.juaovictor0101.post_api.dto;

import com.juaovictor0101.post_api.model.Post;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String text,
        Boolean archived,
        Long userId,
        String authorUsername,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponseDTO fromEntity(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getText(),
                post.getArchived(),
                post.getUser().getId(),
                post.getUser().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}

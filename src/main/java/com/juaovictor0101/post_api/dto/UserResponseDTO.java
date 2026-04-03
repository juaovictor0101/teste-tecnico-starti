package com.juaovictor0101.post_api.dto;

import com.juaovictor0101.post_api.model.User;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String username,
        String name,
        String email,
        String biography,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getBiography(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

package com.juaovictor0101.post_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequestDTO(

        @NotNull(message = "O ID do usuário autor do comentário é obrigatório")
        Long userId,

        @NotNull(message = "O ID da publicação é obrigatório")
        Long postId,

        @NotBlank(message = "O comentário não pode estar vazio")
        @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres")
        String message

) {}

package com.juaovictor0101.post_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(

        @NotNull(message = "O ID do usuário autor da publicação é obrigatório")
        Long userId,

        @NotBlank(message = "O texto da publicação não pode estar vazio")
        @Size(max = 1000, message = "A publicação deve ter no máximo 1000 caracteres")
        String text

) {}

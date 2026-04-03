package com.juaovictor0101.post_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "O nome de usuário é obrigatório")
        @Size(max = 50, message = "O nome de usuário deve ter no máximo 50 caracteres")
        String username,

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O formato do e-mail é inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String password,

        String biography
) {}

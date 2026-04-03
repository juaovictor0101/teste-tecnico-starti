package com.juaovictor0101.post_api.service;

import com.juaovictor0101.post_api.dto.UserRequestDTO;
import com.juaovictor0101.post_api.dto.UserResponseDTO;
import com.juaovictor0101.post_api.model.User;
import com.juaovictor0101.post_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
        }
        if (userRepository.existsByUsername(dto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de usuário já está em uso.");
        }

        User user = User.builder()
                .username(dto.username())
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .biography(dto.biography())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(savedUser);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = findUserEntityById(id);
        return UserResponseDTO.fromEntity(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = findUserEntityById(id);

        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado por outro usuário.");
        }

        if (!user.getUsername().equals(dto.username()) && userRepository.existsByUsername(dto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de usuário já está em uso.");
        }

        user.setUsername(dto.username());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setBiography(dto.biography());

        User updatedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = findUserEntityById(id);
        userRepository.delete(user);
    }

    private User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }
}

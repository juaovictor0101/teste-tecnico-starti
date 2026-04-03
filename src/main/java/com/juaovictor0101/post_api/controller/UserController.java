package com.juaovictor0101.post_api.controller;

import com.juaovictor0101.post_api.dto.CommentResponseDTO;
import com.juaovictor0101.post_api.dto.PostResponseDTO;
import com.juaovictor0101.post_api.dto.UserRequestDTO;
import com.juaovictor0101.post_api.dto.UserResponseDTO;
import com.juaovictor0101.post_api.service.CommentService;
import com.juaovictor0101.post_api.service.PostService;
import com.juaovictor0101.post_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public UserController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Sub-recurso: Listar todos os posts públicos de um usuário
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts(@PathVariable Long id) {
        List<PostResponseDTO> posts = postService.getPublicPostsByUserId(id);
        return ResponseEntity.ok(posts);
    }

    // Sub-recurso: Listar todos os comentários de um usuário em posts públicos
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getUserComments(@PathVariable Long id) {
        List<CommentResponseDTO> comments = commentService.getCommentsByUserIdInPublicPosts(id);
        return ResponseEntity.ok(comments);
    }
}

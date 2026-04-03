package com.juaovictor0101.post_api.controller;

import com.juaovictor0101.post_api.dto.PostRequestDTO;
import com.juaovictor0101.post_api.dto.PostResponseDTO;
import com.juaovictor0101.post_api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO createdPost = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPublicPostsByUserId(@PathVariable Long userId) {
        List<PostResponseDTO> posts = postService.getPublicPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO updatedPost = postService.updatePost(id, dto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<PostResponseDTO> archivePost(@PathVariable Long id) {
        PostResponseDTO archivedPost = postService.archivePost(id);
        return ResponseEntity.ok(archivedPost);
    }
}

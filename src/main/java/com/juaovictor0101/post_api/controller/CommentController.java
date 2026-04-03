package com.juaovictor0101.post_api.controller;

import com.juaovictor0101.post_api.dto.CommentRequestDTO;
import com.juaovictor0101.post_api.dto.CommentResponseDTO;
import com.juaovictor0101.post_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO dto) {
        CommentResponseDTO createdComment = commentService.createComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO dto) {
        CommentResponseDTO updatedComment = commentService.updateComment(id, dto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user/{userId}/public")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByUserIdInPublicPosts(@PathVariable Long userId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByUserIdInPublicPosts(userId);
        return ResponseEntity.ok(comments);
    }
}

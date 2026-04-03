package com.juaovictor0101.post_api.service;

import com.juaovictor0101.post_api.dto.CommentRequestDTO;
import com.juaovictor0101.post_api.dto.CommentResponseDTO;
import com.juaovictor0101.post_api.model.Comment;
import com.juaovictor0101.post_api.model.Post;
import com.juaovictor0101.post_api.model.User;
import com.juaovictor0101.post_api.repository.CommentRepository;
import com.juaovictor0101.post_api.repository.PostRepository;
import com.juaovictor0101.post_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentResponseDTO createComment(CommentRequestDTO dto) {
        User author = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicação não encontrada."));

        if (post.getArchived()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível comentar em uma publicação arquivada.");
        }

        Comment comment = Comment.builder()
                .user(author)
                .post(post)
                .message(dto.message())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDTO.fromEntity(savedComment);
    }

    public CommentResponseDTO updateComment(Long id, CommentRequestDTO dto) {
        Comment comment = findCommentEntityById(id);

        if (!comment.getUser().getId().equals(dto.userId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar este comentário.");
        }

        comment.setMessage(dto.message());
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponseDTO.fromEntity(updatedComment);
    }

    public void deleteComment(Long id) {
        Comment comment = findCommentEntityById(id);
        commentRepository.delete(comment);
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicação não encontrada.");
        }

        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDTO> getCommentsByUserIdInPublicPosts(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        return commentRepository.findByUserIdAndPostArchivedFalse(userId).stream()
                .map(CommentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private Comment findCommentEntityById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado."));
    }
}

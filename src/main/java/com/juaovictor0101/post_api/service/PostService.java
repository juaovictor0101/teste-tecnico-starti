package com.juaovictor0101.post_api.service;

import com.juaovictor0101.post_api.dto.PostRequestDTO;
import com.juaovictor0101.post_api.dto.PostResponseDTO;
import com.juaovictor0101.post_api.model.Post;
import com.juaovictor0101.post_api.model.User;
import com.juaovictor0101.post_api.repository.PostRepository;
import com.juaovictor0101.post_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDTO createPost(PostRequestDTO dto) {
        User author = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário autor não encontrado."));

        Post post = Post.builder()
                .user(author)
                .text(dto.text())
                .archived(false)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponseDTO.fromEntity(savedPost);
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = findPostEntityById(id);
        return PostResponseDTO.fromEntity(post);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO dto) {
        Post post = findPostEntityById(id);

        if (!post.getUser().getId().equals(dto.userId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar esta publicação.");
        }

        post.setText(dto.text());
        Post updatedPost = postRepository.save(post);
        return PostResponseDTO.fromEntity(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = findPostEntityById(id);
        postRepository.delete(post);
    }

    public PostResponseDTO archivePost(Long id) {
        Post post = findPostEntityById(id);
        post.setArchived(true);
        Post archivedPost = postRepository.save(post);
        return PostResponseDTO.fromEntity(archivedPost);
    }

    public List<PostResponseDTO> getPublicPostsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        return postRepository.findByUserIdAndArchivedFalse(userId).stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private Post findPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicação não encontrada."));
    }
}

package com.juaovictor0101.post_api.controller;

import com.juaovictor0101.post_api.dto.PostRequestDTO;
import com.juaovictor0101.post_api.dto.PostResponseDTO;
import com.juaovictor0101.post_api.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@Tag(name = "Publicações", description = "Gerenciamento das publicações (posts) do sistema")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @Operation(summary = "Criar nova publicação", description = "Cria uma nova publicação vinculada a um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Usuário autor não encontrado")
    })
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO createdPost = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar publicação por ID", description = "Retorna os detalhes de uma publicação específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicação encontrada"),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada")
    })
    public ResponseEntity<PostResponseDTO> getPostById(
            @Parameter(description = "ID da publicação", required = true) @PathVariable Long id) {
        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar publicação", description = "Atualiza o texto de uma publicação existente. Somente o autor pode realizar esta ação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é o autor da publicação)"),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada")
    })
    public ResponseEntity<PostResponseDTO> updatePost(
            @Parameter(description = "ID da publicação a ser atualizada", required = true) @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO updatedPost = postService.updatePost(id, dto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar publicação", description = "Remove uma publicação do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publicação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada")
    })
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "ID da publicação a ser deletada", required = true) @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    @Operation(summary = "Arquivar publicação", description = "Altera o status da publicação para arquivada, ocultando-a das listagens públicas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicação arquivada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada")
    })
    public ResponseEntity<PostResponseDTO> archivePost(
            @Parameter(description = "ID da publicação a ser arquivada", required = true) @PathVariable Long id) {
        PostResponseDTO archivedPost = postService.archivePost(id);
        return ResponseEntity.ok(archivedPost);
    }
}

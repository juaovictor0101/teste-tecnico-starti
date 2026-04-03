package com.juaovictor0101.post_api.controller;

import com.juaovictor0101.post_api.dto.CommentRequestDTO;
import com.juaovictor0101.post_api.dto.CommentResponseDTO;
import com.juaovictor0101.post_api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comentários", description = "Gerenciamento dos comentários vinculados às publicações")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(summary = "Criar novo comentário", description = "Adiciona um comentário feito por um usuário em uma publicação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição ou tentativa de comentar em publicação arquivada"),
            @ApiResponse(responseCode = "404", description = "Usuário autor ou publicação não encontrados")
    })
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO dto) {
        CommentResponseDTO createdComment = commentService.createComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar comentário", description = "Edita o texto de um comentário existente. Somente o autor do comentário pode realizar esta ação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é o autor do comentário)"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    public ResponseEntity<CommentResponseDTO> updateComment(
            @Parameter(description = "ID do comentário a ser atualizado", required = true) @PathVariable Long id,
            @Valid @RequestBody CommentRequestDTO dto) {
        CommentResponseDTO updatedComment = commentService.updateComment(id, dto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar comentário", description = "Remove um comentário do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID do comentário a ser deletado", required = true) @PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Listar comentários de uma publicação", description = "Retorna todos os comentários vinculados a uma publicação específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada")
    })
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPostId(
            @Parameter(description = "ID da publicação", required = true) @PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}

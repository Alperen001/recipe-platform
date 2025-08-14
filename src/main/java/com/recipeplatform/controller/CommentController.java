package com.recipeplatform.controller;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.CommentService;
import com.recipeplatform.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{recipeId}")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long recipeId, @RequestBody @Valid CommentDto comment,@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(commentService.addCommentToRecipe(recipeId, currentUser, comment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsForRecipe(id));
    }

    @PreAuthorize("@commentService.isCommentOwner(#id, authentication.principal) or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id,@AuthenticationPrincipal User currentUser) {
        commentService.deleteComment(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.DELETE_COMMENT.getMessage()));
    }
}

package com.recipeplatform.controller;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.dto.UserDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.AdminService;
import com.recipeplatform.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        adminService.deleteUserById(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.DELETE_USER.getMessage()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<ApiResponse> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal User currentUser)
    {
        adminService.deleteRecipeById(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.DELETE_RECIPE.getMessage()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(adminService.getAllComments(currentUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User currentUser)
    {
        adminService.deleteCommentById(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.DELETE_COMMENT.getMessage()));
    }

}

package com.recipeplatform.controller;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.RecipeService;
import com.recipeplatform.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
public class SocialController {

    private final RecipeService recipeService;

    @GetMapping("/favorites")
    public ResponseEntity<List<RecipeDto>> getUserFavorites(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(recipeService.getUserFavoriteRecipes(currentUser));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<ApiResponse> toggleLike(@PathVariable Long id, @AuthenticationPrincipal User currentUser ) {
        recipeService.toggleLike(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_LIKED.getMessage()));
    }

    @PutMapping("/favorite/{id}")
    public ResponseEntity<ApiResponse> toggleFavorite(@PathVariable Long id,@AuthenticationPrincipal User currentUser) {
        recipeService.toggleFavorite(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_FAVORITED.getMessage()));
    }


}

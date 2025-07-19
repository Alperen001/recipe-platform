package com.recipeplatform.controller;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.RecipeService;
import com.recipeplatform.util.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor

public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<ApiResponse> createRecipe(@RequestBody @Valid RecipeDto recipeDto, @AuthenticationPrincipal User currentUser) {
        recipeService.createRecipe(recipeDto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_CREATED.getMessage()));
    }
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity getRecipeById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<ApiResponse> toggleLike(@PathVariable Long id,@AuthenticationPrincipal User currentUser ) {
        recipeService.toggleLike(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_LIKED.getMessage()));
    }
    
    @PutMapping("/{id}/favorite")
    public ResponseEntity<ApiResponse> toggleFavorite(@PathVariable Long id,@AuthenticationPrincipal User currentUser) {
        recipeService.toggleFavorite(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_FAVORITED.getMessage()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRecipe(@PathVariable Long id,
            @RequestBody @Valid RecipeDto updatedRecipeDto,
            @AuthenticationPrincipal User currentUser) {
        recipeService.updateRecipe(id, updatedRecipeDto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.UPDATE_RECIPE.getMessage()));
    }


    @GetMapping("/favorites")
    public ResponseEntity<List<RecipeDto>> getUserFavorites(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(recipeService.getUserFavoriteRecipes(currentUser));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByIngredient(
            @RequestParam @NotBlank @Size(min = 2, max = 50) String ingredient) {
        return ResponseEntity.ok(recipeService.searchByIngredient(ingredient));
    }





    @GetMapping("/filter")
    public ResponseEntity<List<RecipeDto>> filterByCategory(@RequestParam @Valid String category) {
        return ResponseEntity.ok(recipeService.filterByCategory(category));
    }


    @GetMapping("/sortbytime")
    public ResponseEntity<List<RecipeDto>> sortRecipesByTime(@RequestParam(defaultValue = "asc") String order) {
        return ResponseEntity.ok(recipeService.sortByTotalTime(order));
    }

    @GetMapping("/sortBy")
    public ResponseEntity<List<RecipeDto>> sortByCriteria(@RequestParam String sortBy) {
        return ResponseEntity.ok(recipeService.sortBy(sortBy));
    }


}

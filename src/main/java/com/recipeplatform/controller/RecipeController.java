package com.recipeplatform.controller;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.RecipeService;
import com.recipeplatform.service.UserService;
import com.recipeplatform.util.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor

public class RecipeController {

    private final RecipeService recipeService;

    @PreAuthorize("@recipeService.isRecipeOwner(#id, authentication.principal) or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRecipe(@PathVariable @Valid Long id,
                                                    @AuthenticationPrincipal User currentUser) {
        recipeService.deleteRecipe(id, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.DELETE_RECIPE.getMessage()));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> createRecipe(@RequestBody @Valid RecipeDto recipeDto, @AuthenticationPrincipal User currentUser) {
        recipeService.createRecipe(recipeDto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RECIPE_CREATED.getMessage()));
    }
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes()
    {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity getRecipeById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRecipe(@PathVariable Long id,
            @RequestBody @Valid RecipeDto updatedRecipeDto,
            @AuthenticationPrincipal User currentUser) {
        recipeService.updateRecipe(id, updatedRecipeDto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.UPDATE_RECIPE.getMessage()));
    }
}

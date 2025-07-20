package com.recipeplatform.controller;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<?> searchByIngredient(
            @RequestParam @NotBlank @Size(min = 2, max = 50) String ingredient) {
        return ResponseEntity.ok(recipeService.searchByIngredient(ingredient));
    }

    @GetMapping("/category")
    public ResponseEntity<List<RecipeDto>> filterByCategory(@RequestParam @Valid String category) {
        return ResponseEntity.ok(recipeService.filterByCategory(category));
    }

    @GetMapping("/sortbytime")
    public ResponseEntity<List<RecipeDto>> sortRecipesByTime(@RequestParam(defaultValue = "asc") String order) {
        return ResponseEntity.ok(recipeService.sortByTotalTime(order));
    }

}

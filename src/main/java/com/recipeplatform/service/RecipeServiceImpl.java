package com.recipeplatform.service;


import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Category;
import com.recipeplatform.exception.CustomAccessDeniedException;
import com.recipeplatform.exception.ResourceNotFoundException;
import com.recipeplatform.mapping.RecipeMapper;
import com.recipeplatform.repository.RecipeRepository;
import com.recipeplatform.repository.UserRepository;
import com.recipeplatform.util.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static com.recipeplatform.util.HasDeletePermission.hasDeletePermission;
import static com.recipeplatform.util.HasDeletePermission.hasUpdatePermission;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final UserRepository userRepo;

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto, User currentUser) {
        return RecipeMapper.toDto(recipeRepo.save(RecipeMapper.toEntity(recipeDto, currentUser)));
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        return recipeRepo.findAll().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        return RecipeMapper.toDto(recipeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarif bulunamadı: " + id)));
    }

    @Override
    public RecipeDto toggleLike(Long recipeId, User currentUser) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarif bulunamadı"));
        boolean liked = currentUser.getLikedRecipes().contains(recipe);
        if (liked) {
            currentUser.getLikedRecipes().remove(recipe);
            recipe.setLikes(Math.max(0, recipe.getLikes() - 1));
        } else {
            currentUser.getLikedRecipes().add(recipe);
            recipe.setLikes(recipe.getLikes() + 1);
        }
        userRepo.save(currentUser);
        recipeRepo.save(recipe);
        return RecipeMapper.toDto(recipe);
    }


    @Transactional
    @Override
    public RecipeDto toggleFavorite(Long recipeId, User currentUser) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarif bulunamadı"));
        boolean isFavorite = currentUser.getFavoriteRecipes()
                .stream()
                .anyMatch(r -> Objects.equals(r.getId(), recipeId));
        if (isFavorite) {
            currentUser.getFavoriteRecipes().removeIf(r -> Objects.equals(r.getId(), recipeId));
        } else {
            currentUser.getFavoriteRecipes().add(recipe);
        }
        userRepo.save(currentUser);
        recipe.setSaves(userRepo.countByFavoriteRecipesContains(recipe));
        recipeRepo.save(recipe);
        return RecipeMapper.toDto(recipe, !isFavorite);
    }

    @Override
    public List<RecipeDto> getUserFavoriteRecipes(User currentUser) {
        if (currentUser.getFavoriteRecipes() == null || currentUser.getFavoriteRecipes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorilere Eklenen Tarif Yok");
        }
        return currentUser.getFavoriteRecipes().stream()
                .map(recipe -> RecipeMapper.toDto(recipe, true))
                .collect(Collectors.toList());
    }
    @Override
    public Object searchByIngredient(String ingredient) {
        List<Recipe> recipes = recipeRepo.findByIngredientContainingIgnoreCase(ingredient);

        if (recipes.isEmpty()) {
            return new ApiResponse("Bu içeriğe sahip hiçbir tarif bulunamadı.");
        }
        return recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }





    @Override
    public List<RecipeDto> filterByCategory(String categoryStr) {
        Category category;
        try {
            category = Category.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kategori bulunamadı");
        }
        return recipeRepo.findByCategory(category).stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> sortByTotalTime(String order) {
        Comparator<Recipe> comparator = Comparator.comparingInt(
                r -> (r.getPrepTime() != null ? r.getPrepTime() : 0)
                        + (r.getCookTime() != null ? r.getCookTime() : 0)
        );
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return recipeRepo.findAll().stream()
                .sorted(comparator)
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto deleteRecipe(Long recipeId, User currentUser) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarif bulunamadı."));
        if (!hasDeletePermission(recipe, currentUser)) {
            throw new ResourceNotFoundException("Silme yetkiniz yok.");
        }
        recipeRepo.delete(recipe);
        return RecipeMapper.toDto(recipe);
    }


    @Override
    public RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto, User currentUser) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarif bulunamadı."));

        if (!hasUpdatePermission(recipe, currentUser)) {
            throw new CustomAccessDeniedException("Bu tarifi güncelleme yetkiniz yok.");
        }
        RecipeMapper.updateEntity(recipe, recipeDto);
        Recipe saved = recipeRepo.save(recipe);
        return RecipeMapper.toDto(saved);
    }
}

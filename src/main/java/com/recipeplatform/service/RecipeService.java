package com.recipeplatform.service;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Category;

import java.util.List;

public interface RecipeService {


    RecipeDto createRecipe(RecipeDto recipeDto, User currentUser);

    List<RecipeDto> getAllRecipes();

    RecipeDto getRecipeById(Long id);

    RecipeDto toggleLike(Long id, User currentUser);

    RecipeDto toggleFavorite(Long recipeId, User currentUser);

    List<RecipeDto> getUserFavoriteRecipes(User currentUser);

    Object searchByIngredient(String ingredient);

    List<RecipeDto> filterByCategory(String category);

    List<RecipeDto> sortByTotalTime(String order);

    List<RecipeDto> sortBy(String sortBy);

    RecipeDto deleteRecipe(Long id, User currentUser);

    RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto, User currentUser);

}

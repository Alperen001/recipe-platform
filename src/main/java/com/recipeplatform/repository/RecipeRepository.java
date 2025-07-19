package com.recipeplatform.repository;

import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByAuthorUsername(String username);
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(i) LIKE LOWER(CONCAT('%', :ingredient, '%'))")
    List<Recipe> findByIngredientContainingIgnoreCase(@Param("ingredient") String ingredient);
    List<Recipe> findByCategory(Category category);
    int countByAuthorUsername(String authorUsername);
}



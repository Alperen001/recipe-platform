package com.recipeplatform.mapping;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Category;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
@Component
public class RecipeMapper {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    public static Recipe toEntity(RecipeDto dto) {
        Recipe entity = new Recipe();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setIngredients(dto.getIngredients());
        entity.setSteps(dto.getSteps());
        entity.setPrepTime(dto.getPrepTime());
        entity.setCookTime(dto.getCookTime());
        entity.setCategory(dto.getCategory());
        entity.setLikes(dto.getLikes());
        entity.setSaves(dto.getSaves());
        entity.setCreatedAt(new Date());
        return entity;
    }

    public static Recipe toEntity(RecipeDto dto, User user) {
        Recipe entity = new Recipe();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setIngredients(dto.getIngredients());
        entity.setSteps(dto.getSteps());
        entity.setPrepTime(dto.getPrepTime());
        entity.setCookTime(dto.getCookTime());
        entity.setCategory(dto.getCategory());
        entity.setLikes(0);
        entity.setSaves(0);
        entity.setAuthorUsername(user.getUserName());
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());

        return entity;
    }

    public static RecipeDto toDto(Recipe entity) {
        return RecipeDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .ingredients(entity.getIngredients())
                .steps(entity.getSteps())
                .prepTime(entity.getPrepTime())
                .cookTime(entity.getCookTime())
                .likes(entity.getLikes())
                .saves(entity.getSaves())
                .category(entity.getCategory())
                .authorUsername(entity.getAuthorUsername())
                .createdAt(entity.getCreatedAt() != null ? dateFormatter.format(entity.getCreatedAt()) : null)
                .updatedAt(entity.getUpdatedAt() != null ? dateFormatter.format(entity.getUpdatedAt()) : null)
                .comments(entity.getComments().stream()
                        .map(c -> CommentDto.builder()
                                .id(c.getId())
                                .authorEmail(c.getUser() != null ? c.getUser().getEmail() : null)
                                .content(c.getContent())
                                .createdAt(c.getCreatedAt())
                                .build())
                        .toList())
                .build();
    }
    public static RecipeDto toDto(Recipe entity, boolean isFavorited) {
        RecipeDto dto = toDto(entity);
        dto.setFavorited(isFavorited);
        return dto;
    }

    public static void updateEntity(Recipe recipe, RecipeDto dto) {
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setSteps(dto.getSteps());
        recipe.setCategory(dto.getCategory());
        recipe.setPrepTime(dto.getPrepTime());
        recipe.setCookTime(dto.getCookTime());
        recipe.setUpdatedAt(new Date());
    }

}


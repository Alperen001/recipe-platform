package com.recipeplatform.mapping;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.entity.Comment;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {

    public static CommentDto toDto(Comment entity) {
        if (entity == null) return null;

        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .authorEmail(entity.getUser().getEmail())
                .recipeId(entity.getRecipe().getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }


    public static Comment toEntity(CommentDto dto, User user, Recipe recipe) {
        if (dto == null) return null;
        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setUser(user);
        entity.setRecipe(recipe);
        entity.setCreatedAt(new Date());
        return entity;
    }
}

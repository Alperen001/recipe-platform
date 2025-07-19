package com.recipeplatform.service;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.entity.User;

import java.util.List;

public interface CommentService {
    CommentDto addCommentToRecipe(Long recipeId, User currentUser, CommentDto comment);

    List<CommentDto> getCommentsForRecipe(Long recipeId);

    CommentDto deleteComment(Long id, User currentUser);
}

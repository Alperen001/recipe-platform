package com.recipeplatform.service;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.dto.UserDto;
import com.recipeplatform.entity.User;

import java.util.List;

public interface AdminService {

    UserDto deleteUserById(Long id, User currentUser);

    RecipeDto deleteRecipeById(Long id,User currentUser);

    List<CommentDto> getAllComments(User currentUser);

    CommentDto deleteCommentById(Long id,User currentUser);

}

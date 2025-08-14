package com.recipeplatform.service;

import com.recipeplatform.dto.*;
import com.recipeplatform.entity.User;


import java.util.List;

public interface UserService {
    RegisterRequestDto register(UserDto userDto);

    LoginResponseDto login(UserDto user);

    ResetPasswordRequest resetPassword(ResetPasswordRequest request);

    UserProfileDto getCurrentUserProfile(User currentUser);

    List<RecipeDto> getRecipesByUsername(String username);

    void resetOwnPassword(ResetPasswordRequest request, User currentUser);

}


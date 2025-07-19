package com.recipeplatform.dto;

import com.recipeplatform.enums.Gender;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UserProfileDto {
    private String email;
    private String name;
    private String surName;
    private String userName;
    private Gender gender;
    private int totalRecipes;
    private int totalFavorites;
    private int totalLikes;
}
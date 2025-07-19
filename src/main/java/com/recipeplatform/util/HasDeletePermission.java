package com.recipeplatform.util;

import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Role;

public class HasDeletePermission {
    public static boolean hasDeletePermission(Recipe recipe, User user) {
        return isAuthor(recipe, user) || isAdmin(user);
    }

    public static boolean hasUpdatePermission(Recipe recipe, User user) {
        return isAuthor(recipe, user) || isAdmin(user);
    }

    private static boolean isAuthor(Recipe recipe, User user) {
        return recipe.getAuthorUsername() != null &&
                recipe.getAuthorUsername().equalsIgnoreCase(user.getUserName());
    }

    private static boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }
}

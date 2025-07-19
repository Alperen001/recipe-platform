package com.recipeplatform.mapping;

import com.recipeplatform.dto.RegisterRequestDto;
import com.recipeplatform.dto.ResetPasswordRequest;
import com.recipeplatform.dto.UserDto;

import com.recipeplatform.dto.UserProfileDto;
import com.recipeplatform.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public static User toEntity(UserDto dto) {
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.getGender());
        entity.setUserName(dto.getUserName());
        entity.setSurName(dto.getSurName());
        entity.setPassword(dto.getPassword());
        entity.setId(dto.getId());
        entity.setRole(dto.getRole());
        return entity;
    }

    public static UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setGender(entity.getGender());
        dto.setId(entity.getId());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        dto.setSurName(entity.getSurName());
        dto.setRole(entity.getRole());
        return dto;

    }

    public static RegisterRequestDto toRegisterDto(User user) {
        return RegisterRequestDto.builder()
                .message("Kullanıcı başarıyla oluşturuldu")
                .build();
    }

    public static UserProfileDto toUserProfileDto(User user, int totalRecipes,int totalFavorites) {
        return UserProfileDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surName(user.getSurName())
                .userName(user.getUserName())
                .gender(user.getGender())
                .totalRecipes(totalRecipes)
                .totalFavorites(user.getFavoriteRecipes() != null ? user.getFavoriteRecipes().size() : 0)
                .totalLikes(user.getLikedRecipes() != null ? user.getLikedRecipes().size() : 0)
                .build();
    }

    public static ResetPasswordRequest toResetDto(User user) {
        return ResetPasswordRequest.builder()
                .message("Şifre başarıyla değiştirildi")
                .build();
    }
}

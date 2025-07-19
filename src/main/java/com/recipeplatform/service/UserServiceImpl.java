package com.recipeplatform.service;

import com.recipeplatform.config.JwtUtil;
import com.recipeplatform.dto.*;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Role;
import com.recipeplatform.mapping.RecipeMapper;
import com.recipeplatform.mapping.UserMapper;
import com.recipeplatform.repository.RecipeRepository;
import com.recipeplatform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RecipeRepository recipeRepository;

    @Override
    public RegisterRequestDto register(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email zaten kullanılıyor.");
        }
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Kullanıcı adı (username) zaten kullanılıyor.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return UserMapper.toRegisterDto(userRepository.save(user));
    }

    @Override
    public LoginResponseDto login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kullanıcı bulunamadı"));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Hatalı şifre");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponseDto(token);
    }
    @Override
    public ResetPasswordRequest resetPassword(ResetPasswordRequest request) {
        if (request.getEmail() == null || request.getNewPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ve yeni şifre boş olamaz.");
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı."));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return UserMapper.toResetDto(userRepository.save(user));
    }


    @Override
    @Transactional
    public UserProfileDto getCurrentUserProfile(User currentUser) {
        return UserMapper.toUserProfileDto(currentUser,
                recipeRepository.countByAuthorUsername(currentUser.getUserName()),
                currentUser.getFavoriteRecipes() != null ? currentUser.getFavoriteRecipes().size() : 0);
    }

    @Override
    public List<RecipeDto> getRecipesByUsername(String username) {
        List<Recipe> userRecipes = recipeRepository.findByAuthorUsername(username);

        if (userRecipes == null || userRecipes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı tarif oluşturmadı.");
        }

        return userRecipes.stream()
                .map(recipe -> RecipeMapper.toDto(recipe, false))
                .collect(Collectors.toList());
    }

}

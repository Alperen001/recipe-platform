package com.recipeplatform.service;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.dto.RecipeDto;
import com.recipeplatform.dto.UserDto;
import com.recipeplatform.entity.Comment;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Role;
import com.recipeplatform.mapping.CommentMapper;
import com.recipeplatform.mapping.RecipeMapper;
import com.recipeplatform.mapping.UserMapper;
import com.recipeplatform.repository.CommentRepository;
import com.recipeplatform.repository.RecipeRepository;
import com.recipeplatform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;

    @Override
    public UserDto deleteUserById(Long id, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Yetkisiz erişim");
        }

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı"));
        userRepository.delete(userToDelete);
        return UserMapper.toDto(userToDelete);
    }

    @Override
    public RecipeDto deleteRecipeById(Long id,User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Yetkisiz erişim");
        }
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarif bulunamadı"));
        recipeRepository.delete(recipe);
        return RecipeMapper.toDto(recipe);
    }

    @Override
    public List<CommentDto> getAllComments(User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Yetkisiz erişim");
        }
        return commentRepository.findAll()
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }
    @Override
    public CommentDto deleteCommentById(Long id,User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Yetkisiz erişim");
        }
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yorum bulunamadı"));
        commentRepository.delete(comment);
        return CommentMapper.toDto(comment);
    }
}

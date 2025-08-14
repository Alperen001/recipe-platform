package com.recipeplatform.service;

import com.recipeplatform.dto.CommentDto;
import com.recipeplatform.entity.Comment;
import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import com.recipeplatform.exception.CustomAccessDeniedException;
import com.recipeplatform.exception.ResourceNotFoundException;
import com.recipeplatform.mapping.CommentMapper;
import com.recipeplatform.repository.CommentRepository;
import com.recipeplatform.repository.RecipeRepository;
import com.recipeplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final RecipeRepository recipeRepo;


    @Override
    public CommentDto addCommentToRecipe(Long recipeId, User currentUser, CommentDto commentDto) {
        Comment comment = CommentMapper.toEntity(commentDto, userRepo.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı")), recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarif bulunamadı")));
        return CommentMapper.toDto(commentRepo.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsForRecipe(Long recipeId) {
        return commentRepo.findByRecipeId(recipeId).stream()
                .map(c -> CommentDto.builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .authorEmail(c.getUser().getEmail())
                        .createdAt(c.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto deleteComment(Long commentId, User currentUser) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Yorum bulunamadı."));
        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new CustomAccessDeniedException("Bu yorumu silmeye yetkiniz yok.");
        }
        commentRepo.delete(comment);
        return CommentMapper.toDto(comment);
    }


    @Override
    public boolean isCommentOwner(Long commentId, User currentUser) {
        return commentRepo.findById(commentId)
                .map(comment -> comment.getUser().getId().equals(currentUser.getId()))
                .orElse(false);
    }

}

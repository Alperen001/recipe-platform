package com.recipeplatform.repository;

import com.recipeplatform.entity.Comment;
import com.recipeplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByRecipeId(Long recipeId);
    void deleteAllByUser(User user);


    List<Comment> findAllByUser(User user);
}

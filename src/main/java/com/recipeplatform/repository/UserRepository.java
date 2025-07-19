package com.recipeplatform.repository;

import com.recipeplatform.entity.Recipe;
import com.recipeplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    int countByFavoriteRecipesContains(Recipe recipe);
    Optional<User> findByUserName(String userName);

}

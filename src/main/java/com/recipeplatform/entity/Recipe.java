package com.recipeplatform.entity;

import com.recipeplatform.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"likedByUsers", "favoritedByUsers", "comments"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> steps;

    private Integer prepTime;
    private Integer likes;
    private Integer cookTime;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "likedRecipes", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Set<User> likedByUsers = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteRecipes",cascade = CascadeType.REMOVE)
    private Set<User> favoritedByUsers = new HashSet<>();

    private String authorUsername;

    private Date createdAt;

    private Date updatedAt;

    private Integer saves;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe other = (Recipe) o;
        return id != null && id.equals(other.getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }

}



package com.recipeplatform.dto;


import com.recipeplatform.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    @NotBlank(message = "Başlık boş olamaz")
    private String title;

    @NotBlank(message = "Açıklama boş olamaz")
    private String description;

    @NotNull(message = "Malzemeler eksik olamaz")
    private List<String> ingredients;

    @NotNull(message = "Adımlar eksik olamaz")
    private List<String> steps;

    @NotNull(message = "Hazırlık süresi belirtilmeli")
    @Min(value = 1, message = "Hazırlık süresi 0'dan büyük olmalı")
    private Integer prepTime;

    @NotNull(message = "Pişirme süresi belirtilmeli")
    @Min(value = 1, message = "Pişirme süresi 0'dan büyük olmalı")
    private Integer cookTime;

    @NotNull(message = "Kategori belirtilmeli")
    private Category category;

    private List<CommentDto> comments = new ArrayList<>();
    private Integer likes;
    private Integer saves;
    private String createdAt;
    private String updatedAt;
    private String authorUsername;
    private Boolean favorited;
}

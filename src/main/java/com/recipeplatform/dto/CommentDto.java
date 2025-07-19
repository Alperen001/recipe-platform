package com.recipeplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private Long id;
    @NotBlank(message = "Yorum boş olamaz")
    private String content;
    private String authorEmail;
    private Date createdAt;
    private String message;
    private Long recipeId;
    private Long userId;
}

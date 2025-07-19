package com.recipeplatform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "Email boş olamaz")
    private String email;
    @NotBlank(message = "Yeni şifre boş olamaz")
    private String newPassword;
    private String message;
}

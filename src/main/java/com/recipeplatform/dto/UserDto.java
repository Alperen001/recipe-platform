package com.recipeplatform.dto;

import com.recipeplatform.enums.Gender;
import com.recipeplatform.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email boş olamaz")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String password;

    @Column
    private String name;

    @Column
    private String surName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

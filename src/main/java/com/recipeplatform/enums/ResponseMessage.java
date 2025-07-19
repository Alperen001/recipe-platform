package com.recipeplatform.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor

public enum ResponseMessage {
    RECIPE_CREATED("Tarif Başarıyla Oluşturuldu"),
    RECIPE_LIKED("Tarif Beğeni Durumu Güncellendi"),
    RECIPE_FAVORITED("Tarif Favori Durumu Güncellendi"),
    RESET_PASSWORD("Şifre Başarıyla Değiştirildi"),
    UPDATE_RECIPE("Tarif Başarıyla Güncellendi"),
    DELETE_RECIPE("Tarif Başarıyla Silindi"),
    DELETE_COMMENT("Yorum Başarıyla Silindi"),
    DELETE_USER("Kullanıcı Başarıyla Silindi");

    private final String message;

    public String getMessage() {
        return message;
    }

}

package com.recipeplatform.util;

import com.recipeplatform.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHeaderUtil {
    private final JwtUtil jwtUtil;

    public String extractEmail(String authHeader){
        String token = authHeader.replace("Bearer ", "").trim();
        String email = jwtUtil.extractEmail(token);
        return  email;

    }
}

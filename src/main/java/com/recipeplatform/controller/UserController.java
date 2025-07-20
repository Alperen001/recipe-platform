package com.recipeplatform.controller;
import com.recipeplatform.dto.*;
import com.recipeplatform.entity.User;
import com.recipeplatform.enums.ResponseMessage;
import com.recipeplatform.service.CommentService;
import com.recipeplatform.service.RecipeService;
import com.recipeplatform.service.UserService;
import com.recipeplatform.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRequestDto> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.login(userDto));
    }

    @PreAuthorize("#request.userId == authentication.principal.id")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseMessage.RESET_PASSWORD.getMessage()));
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(currentUser));
    }
    @GetMapping("/recipes/{username}")
    public ResponseEntity<List<RecipeDto>> getUserRecipes(@PathVariable String username) {
        return ResponseEntity.ok(userService.getRecipesByUsername(username));
    }

}

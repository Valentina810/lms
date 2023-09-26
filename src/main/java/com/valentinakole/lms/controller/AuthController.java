package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.jwt.AuthenticationRequestDto;
import com.valentinakole.lms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    @Operation(summary = "Получение токена по логин и пароль")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return authService.createAuthToken(authenticationRequestDto);
    }
}

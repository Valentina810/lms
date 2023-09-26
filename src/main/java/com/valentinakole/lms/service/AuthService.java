package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.jwt.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> createAuthToken(AuthenticationRequestDto authenticationRequestDto);
}

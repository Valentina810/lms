package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.jwt.AuthenticationRequestDto;
import com.valentinakole.lms.dto.jwt.AuthenticationResponseDto;
import com.valentinakole.lms.security.JwtTokenUtils;
import com.valentinakole.lms.service.AuthService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public ResponseEntity<?> createAuthToken(AuthenticationRequestDto auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.status(201).body(new AuthenticationResponseDto(token));
    }
}

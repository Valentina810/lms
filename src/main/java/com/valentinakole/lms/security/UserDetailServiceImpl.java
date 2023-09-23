package com.valentinakole.lms.security;

import com.valentinakole.lms.model.User;
import com.valentinakole.lms.security.UserDetailsImpl;
import com.valentinakole.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с '%s' не найден", username)));
        return new UserDetailsImpl(user);
    }
}

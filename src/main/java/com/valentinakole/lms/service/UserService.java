package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    User findById(long id);

    User create(User user);

    UserResponseDto update(long id, Map<String, Object> map);

    Optional<User> findByEmail(String email);
}

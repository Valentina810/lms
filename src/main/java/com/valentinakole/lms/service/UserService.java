package com.valentinakole.lms.service;

import com.valentinakole.lms.models.User;

import java.util.Optional;

public interface UserService {
    User findById(long id);

    User create(User user);

    User update(long id, User updatedUser);

    Optional<User> findByEmail(String email);
}

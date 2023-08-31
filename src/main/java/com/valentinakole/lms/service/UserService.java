package com.valentinakole.lms.service;

import com.valentinakole.lms.models.User;
import com.valentinakole.lms.repositories.UserRepository;
import com.valentinakole.lms.util.UserNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundError::new);
    }

    @Transactional
    public User create(User user) {
        user.setToken(String.valueOf((int) (Math.random() * 1000000000)));
        user.setDateRegistration(new Date());
        return userRepository.save(user);
    }

    @Transactional
    public User update(long id, User updatedUser) {
        User user = findById(id);
        updatedUser.setId_user(id);
        updatedUser.setToken(user.getToken());
        updatedUser.setDateRegistration(user.getDateRegistration());
        return userRepository.save(updatedUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}

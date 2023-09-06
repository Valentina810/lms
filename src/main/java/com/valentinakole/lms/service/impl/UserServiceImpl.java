package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findById(long id) {
        log.info("The user with id {} was found", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь", id));
    }

    @Transactional
    public User create(User user) {
        user.setToken(String.valueOf((int) (Math.random() * 1000000000)));
        user.setDateRegistration(new Date());
        user = userRepository.save(user);
        log.info("The user with id {} was created", user.getId_user());
        return user;
    }

    @Transactional
    public User update(long id, User updatedUser) {
        User user = findById(id);
        updatedUser.setId_user(id);
        updatedUser.setToken(user.getToken());
        updatedUser.setDateRegistration(user.getDateRegistration());
        log.info("The user with id {} was updated", user.getId_user());
        return userRepository.save(updatedUser);
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            log.info("The user with email {} was found", email);
        } else {
            log.info("The user with email {} was not found", email);
        }
        return optional;
    }
}

package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.model.Role;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findById(long id) {
        log.info("Пользователь  с id {} найден", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь", id));
    }

    @Transactional
    public User create(User user) {
        user.setToken(String.valueOf((int) (Math.random() * 1000000000)));
        user.setDateRegistration(LocalDate.now());
        user = userRepository.save(user);
        user.setRole(Role.USER);
        log.info("Пользователь  с id {} создан", user.getUserId());
        return user;
    }

    @Transactional
    public User update(long id, User user) {
        if (Objects.equals(user.getPassword(), "")) {
            userRepository.updateWithoutPassword(user.getName(), user.getSurname(), user.getLogin(), user.getEmail(),
                    user.getDateBirth(), user.getAvatarUrl(), id);
        } else {
            userRepository.updateWithPassword(user.getName(), user.getSurname(), user.getLogin(), user.getPassword(),
                    user.getEmail(), user.getDateBirth(), user.getAvatarUrl(), id);
        }
        User updatedUser = findById(id);
        log.info("Пользователь  с id {} обновлен", updatedUser.getUserId());
        return userRepository.save(updatedUser);
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            log.info("Пользователь  с email {} найден", email);
        } else {
            log.info("Пользователь  с email {} не найден", email);
        }
        return optional;
    }
}

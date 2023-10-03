package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.UpdateUserMapper;
import com.valentinakole.lms.mapper.UserMapper;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidateUpdatedFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UpdateUserMapper updateUserMapper;
    private final UserMapper userMapper;
    private final ValidateUpdatedFields validateUpdatedFields;

    public User findById(long id) {
        log.info("The user with id {} was found", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь", id));
    }

    @Transactional
    public User create(User user) {
        user.setToken(String.valueOf((int) (Math.random() * 1000000000)));
        user.setDateRegistration(LocalDate.now());
        user = userRepository.save(user);
        log.info("The user with id {} was created", user.getUserId());
        return user;
    }

    @Transactional
    public UserResponseDto update(long id, UserRequestDto userRequestDto) {

        User user = findById(id);

        userRequestDto = updateUserMapper.toUserRequestDto(user, userRequestDto);

        validateUpdatedFields.validate(id, userRequestDto);

        userRepository.updateWithPassword(userRequestDto.getName(), userRequestDto.getSurname(), userRequestDto.getLogin(), userRequestDto.getPassword(),
                userRequestDto.getEmail(), LocalDate.parse(userRequestDto.getDateBirth()), userRequestDto.getAvatarUrl(), id);
        log.info("The user with id {} was updated", id);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(userRequestDto);
        userResponseDto.setUserId(id);
        return userResponseDto;
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

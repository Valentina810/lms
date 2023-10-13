package com.valentinakole.lms.service.impl;

import com.querydsl.core.types.Path;
import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.UserMapper;
import com.valentinakole.lms.model.QUser;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.GenerateListsForQueryDsl;
import com.valentinakole.lms.util.ListPaths;
import com.valentinakole.lms.util.validation.ValidationEmail;
import com.valentinakole.lms.util.validation.ValidationFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final GenerateListsForQueryDsl generate;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ModelMapper mapper;
    private final ValidationEmail validationEmail;
    private final ValidationFields validationFields;

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
    public UserResponseDto update(long userId, Map<String, Object> map){

        User user = findById(userId);
        UserRequestDto updatedUserRequestDto = mapper.map(map, UserRequestDto.class);
        UserRequestDto userRequestDto = userMapper.toUserRequestDto(user);
        mapper.map(updatedUserRequestDto, userRequestDto);

        validationFields.validate(userRequestDto);
        validationEmail.validate(userRequestDto.getEmail(), userId);

        user = userMapper.toUser(userRequestDto);
        user.setUserId(userId);

        Map<String, Path<?>> paths = new ListPaths().get(new QUser("user"));
        Map<String, List<?>> pathsAndValue = generate.get(user, map, paths);

        long counter = userRepository.update(userId, (List<Path<?>>) pathsAndValue.get("path"), pathsAndValue.get("value"));
        if (counter != 1) {
            throw new NotFoundException("Пользователь", userId);
        }

        return userMapper.toUserResponseDto(user);
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

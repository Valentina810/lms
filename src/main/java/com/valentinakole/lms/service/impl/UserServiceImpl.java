package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.user.UserDto;
import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.UserMapper;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidateUpdatedFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidateUpdatedFields validateUpdatedFields;
    private final ModelMapper mapper;

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
        UserDto userDto = userMapper.toUserDto(user);
        mapper.map(userRequestDto, userDto);
        validateUpdatedFields.validate(userDto);
        userRepository.updateWithPassword(userDto.getName(), userDto.getSurname(), userDto.getLogin(), userDto.getPassword(),
                userDto.getEmail(), LocalDate.parse(userDto.getDateBirth()), userDto.getAvatarUrl(), id);
        log.info("The user with id {} was updated", id);
        return userMapper.toUserResponseDto(userDto);
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

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}

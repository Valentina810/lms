package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.user.FullUserDto;
import com.valentinakole.lms.dto.user.UserCreateDto;
import com.valentinakole.lms.dto.user.UserUpdateDto;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.UserMapper;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.valentinakole.lms.util.validate.ValidationMessage.DUPLICATE_EMAIL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public FullUserDto findById(long id) {
        log.info("The user with id {} was found", id);
        return userMapper.toFullUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь", id)));
    }

    @Transactional
    public FullUserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toUser(userCreateDto);
        user.setToken(String.valueOf((int) (Math.random() * 1000000000)));
        user.setDateRegistration(LocalDate.now());
        user = userRepository.save(user);
        log.info("The user with id {} was created", user.getUserId());
        return userMapper.toFullUserDto(user);
    }

    @Transactional
    public FullUserDto update(long id, UserUpdateDto userUpdateDto) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
        User updatedUser = userMapper.toUser(userUpdateDto);
        updatedUser.setUserId(id);
        if (isEmailUnique(updatedUser)) {
            throw new EmailExistError(DUPLICATE_EMAIL);
        }
        BeanUtils.copyProperties(updatedUser, oldUser, getNullPropertyNames(updatedUser));
        return userMapper.toFullUserDto(userRepository.save(oldUser));
    }

    private boolean isEmailUnique(User updatedUser) {
        if (updatedUser.getEmail() != null) {
            Optional<User> user = userRepository.findByEmail(updatedUser.getEmail());
            if (user.isPresent()) {
                return !user.get().getUserId().equals(updatedUser.getUserId());
            }
        }
        return false;
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

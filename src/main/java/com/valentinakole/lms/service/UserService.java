package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.user.FullUserDto;
import com.valentinakole.lms.dto.user.UserCreateDto;
import com.valentinakole.lms.dto.user.UserUpdateDto;

public interface UserService {
    FullUserDto findById(long id);

    FullUserDto create(UserCreateDto userCreateDto);

    FullUserDto update(long id, UserUpdateDto userUpdateDto);
}

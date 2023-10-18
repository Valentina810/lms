package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.user.FullUserDto;
import com.valentinakole.lms.dto.user.UserCreateDto;
import com.valentinakole.lms.dto.user.UserUpdateDto;
import com.valentinakole.lms.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateDto userCreateDto);

    User toUser(UserUpdateDto userUpdateDto);

    FullUserDto toFullUserDto(User user);
}
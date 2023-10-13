package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.user.UserCreateDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.dto.user.UserResponseGetDto;
import com.valentinakole.lms.dto.user.UserUpdateDto;
import com.valentinakole.lms.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserUpdateDto userUpdateDto);

    User toUser(UserCreateDto userCreateDto);

    UserResponseDto toUserResponseDto(User user);

    UserResponseGetDto toUserResponseGetDto(User user);
}
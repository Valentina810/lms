package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserMapper {

    public UserRequestDto toUserRequestDto(User user, UserRequestDto userRequestDto) {

        if (userRequestDto.getName() != null) {
            userRequestDto.setName(userRequestDto.getName());
        } else {
            userRequestDto.setName(user.getName());
        }
        if (userRequestDto.getSurname() != null) {
            userRequestDto.setSurname(userRequestDto.getSurname());
        } else {
            userRequestDto.setSurname(user.getSurname());
        }
        if (userRequestDto.getLogin() != null) {
            userRequestDto.setLogin(userRequestDto.getLogin());
        } else {
            userRequestDto.setLogin(user.getLogin());
        }
        if (userRequestDto.getPassword() != null) {
            userRequestDto.setPassword(userRequestDto.getPassword());
        } else {
            userRequestDto.setPassword(user.getPassword());
        }
        if (userRequestDto.getEmail() != null) {
            userRequestDto.setEmail(userRequestDto.getEmail());
        } else {
            userRequestDto.setEmail(user.getEmail());
        }
        if (userRequestDto.getDateBirth() != null) {
            userRequestDto.setDateBirth(userRequestDto.getDateBirth());
        } else {
            userRequestDto.setDateBirth(user.getDateBirth().toString());
        }
        if (userRequestDto.getAvatarUrl() != null) {
            userRequestDto.setAvatarUrl(userRequestDto.getAvatarUrl());
        } else {
            userRequestDto.setAvatarUrl(user.getAvatarUrl());
        }
        return userRequestDto;
    }
}

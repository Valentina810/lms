package com.valentinakole.lms.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponsePatchDto {

    private long idUser;

    private String name;

    private String surname;

    private String login;

    private String password;

    private String email;

    private Date dateBirth;

    private String avatarUrl;
}

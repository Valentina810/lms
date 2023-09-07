package com.valentinakole.lms.dto.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseGetDto {

    private long idUser;

    private String name;

    private String surname;

    private String login;

    private String email;

    private LocalDate dateBirth;

    private LocalDate dateRegistration;

    private String avatarUrl;
}

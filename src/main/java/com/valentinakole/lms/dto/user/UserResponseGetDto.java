package com.valentinakole.lms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseGetDto {

    private Long userId;

    private String name;

    private String surname;

    private String login;

    private String email;

    private LocalDate dateBirth;

    private LocalDate dateRegistration;

    private String avatarUrl;
}

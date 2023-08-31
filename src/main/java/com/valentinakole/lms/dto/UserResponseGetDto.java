package com.valentinakole.lms.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponseGetDto {

    private long idUser;
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 250, message = "Name should less than 250 characters")
    private String name;

    @Column(name = "surname")
    @Size(max = 250, message = "Surname should less than 250 characters")
    private String surname;

    @NotEmpty(message = "Login should not be empty")
    @Size(max = 100, message = "Login should less than 100 characters")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    @Size(max = 100, message = "Password should less than 100 characters")
    private String password;

    @NotEmpty(message = "Email should not be empty")
    @Size(max = 250, message = "Email should less than 250 characters")
    @Email
    private String email;

    private Date dateBirth;

    @NotEmpty(message = "Date registration should not be empty")
    private Date dateRegistration;

    @Size(max = 1000, message = "Avatar URL should less than 1000 characters")
    private String avatarUrl;
}

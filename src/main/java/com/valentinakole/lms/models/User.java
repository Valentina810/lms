package com.valentinakole.lms.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;

    @Column(name = "name")
//    @NotEmpty(message = "Name should not be empty")
//    @Size(max = 250, message = "Name should less than 250 characters")
    private String name;

    @Column(name = "surname")
//    @Size(max = 250, message = "Surname should less than 250 characters")
    private String surname;

    @Column(name = "login")
//    @NotEmpty(message = "Login should not be empty")
//    @Size(max = 100, message = "Login should less than 100 characters")
    private String login;

    @Column(name = "password")
//    @NotEmpty(message = "Password should not be empty")
//    @Size(max = 100, message = "Password should less than 100 characters")
    private String password;

    @Column(name = "token")
//    @NotEmpty(message = "Token should not be empty")
//    @Size(max = 100, message = "Token should less than 100 characters")
    private String token;

    @Column(name = "email")
//    @NotEmpty(message = "Email should not be empty")
//    @Size(max = 250, message = "Email should less than 250 characters")
//    @Email
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_birth")
    private Date dateBirth;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_registration")
//    @NotEmpty(message = "Date registration should not be empty")
    private Date dateRegistration;

    @Column(name = "avatar_url")
//    @Size(max = 1000, message = "Avatar URL should less than 1000 characters")
    private String avatarUrl;
}

package com.valentinakole.lms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserTokenDto {

    @NotEmpty(message = "Токен не должен быть пустым")
    @Size(max = 100, message = "Tокен не должен быть больше 100 знаков")
    private String token;
}

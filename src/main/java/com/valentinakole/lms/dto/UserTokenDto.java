package com.valentinakole.lms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserTokenDto {

    @NotEmpty(message = "Token should not be empty")
    @Size(max = 100, message = "Token should less than 100 characters")
    private String token;
}

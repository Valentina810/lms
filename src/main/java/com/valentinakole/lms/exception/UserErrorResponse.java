package com.valentinakole.lms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
public class UserErrorResponse {
    private String message;
    private LocalDateTime date;
}

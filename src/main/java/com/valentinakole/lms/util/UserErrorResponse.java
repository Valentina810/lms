package com.valentinakole.lms.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserErrorResponse {
    private String message;
    private long timestamp;
}

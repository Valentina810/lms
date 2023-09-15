package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidatePathVariable {
    public void check(Long id) {
        if (id < 0) {
            throw new NotFoundException("Пользователь", id);
        }
    }
}

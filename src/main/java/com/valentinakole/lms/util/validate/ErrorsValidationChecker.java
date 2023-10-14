package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.BadRequestError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsValidationChecker {
    public static void checkValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            throw new BadRequestError(errorMessage.toString());
        }
    }
}
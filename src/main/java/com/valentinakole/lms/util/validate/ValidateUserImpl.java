package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidateUserImpl implements ValidateUser {
    private final UserValidator userValidator;

    @Override
    public User validateUser(UserRequestDto userRequestDto, BindingResult bindingResult, long id) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        user.setId_user(id);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            throw new BadRequestError(errorMessage.toString());
        }
        userValidator.validate(user, bindingResult);
        return user;
    }
}

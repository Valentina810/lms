package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class ValidateUserImpl implements ValidateUser {
    private final ValidateUserEmailExist validateUserEmailExist;

    @Override
    public User validateUser(User user, BindingResult bindingResult) {
        Checker.checkValidationErrors(bindingResult);
        validateUserEmailExist.validate(user, bindingResult);
        return user;
    }
}

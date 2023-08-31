package com.valentinakole.lms.util;

import com.valentinakole.lms.models.User;
import com.valentinakole.lms.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        Optional<User> optional = userService.findByEmail(user.getEmail());
        if (optional.isPresent() && optional.get().getId_user() != user.getId_user()) {
            errors.rejectValue("email", "", "This email already taken");
            throw new EmailExistError();
        }
    }
}

package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.impl.UserServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserServiceImpl userService;

    public UserValidator(UserServiceImpl userService) {
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

package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.model.User;
import org.springframework.validation.BindingResult;

public interface ValidateUser {
    User validateUser(User user, BindingResult bindingResult);
}

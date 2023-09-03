package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.model.User;
import org.springframework.validation.BindingResult;

public interface ValidateUser {
    User validateUser(UserRequestDto userRequestDto, BindingResult bindingResult, long id);
}

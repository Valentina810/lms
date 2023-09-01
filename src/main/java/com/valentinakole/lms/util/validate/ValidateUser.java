package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.dto.UserRequestDto;
import com.valentinakole.lms.models.User;
import org.springframework.validation.BindingResult;

public interface ValidateUser {
    User validateUser(UserRequestDto userRequestDto, BindingResult bindingResult, long id);
}

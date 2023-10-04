package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.dto.user.UserDto;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidateUpdatedFields {

    private final Validator validator;
    private final UserRepository userRepository;

    public void validate(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if (!violations.isEmpty()) {
            String error = violations.stream().map(e -> e.getMessage() + " ;").collect(Collectors.joining());
            throw new ValidationException(error);
        }
        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());
        if (optional.isPresent() && optional.get().getUserId() != userDto.getUserId()) {
            throw new EmailExistError("Эта электронная почта уже существует в базе данных");
        }
    }
}

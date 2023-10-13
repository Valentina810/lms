package com.valentinakole.lms.util.validation;

import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationEmail {
    private final UserRepository userRepository;

    public void validate(String email, Long userId) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent() && optional.get().getUserId() != userId) {
            throw new EmailExistError("Эта электронная почта уже существует в базе данных");
        }
    }
}

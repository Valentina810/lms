package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.BadRequestError;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class ValidateAvatarUrl {
    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException e) {
            throw new BadRequestError("Не правильный формат url");
        } catch (URISyntaxException e) {
            throw new BadRequestError("Не правильный формат url");
        }
    }
}

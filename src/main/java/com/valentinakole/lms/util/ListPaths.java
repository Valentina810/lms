package com.valentinakole.lms.util;

import com.querydsl.core.types.Path;
import com.valentinakole.lms.exception.errors.NoSuchFieldError;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ListPaths {
    public Map<String, Path<?>> get(Object entity) {
        Map<String, Path<?>> paths = new HashMap<>();

        Class<?> clazz = entity.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new NoSuchFieldError(e.getMessage());
            }
            if (field.getGenericType().getTypeName().equals("com.querydsl.core.types.dsl.StringPath") ||
                    field.getGenericType().getTypeName().equals("com.querydsl.core.types.dsl.DatePath<java.time.LocalDate>")) {
                paths.put(fieldName, (Path<?>) fieldValue);
            }
        }
//
//        paths.put("name", QUser.user.name);
//        paths.put("surname", QUser.user.surname);
//        paths.put("password", QUser.user.password);
//        paths.put("email", QUser.user.email);
//        paths.put("login", QUser.user.login);
//        paths.put("avatarUrl", QUser.user.avatarUrl);
//        paths.put("dateBirth", QUser.user.dateBirth);
//
        return paths;
    }
}

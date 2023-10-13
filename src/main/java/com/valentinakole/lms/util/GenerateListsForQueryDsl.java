package com.valentinakole.lms.util;

import com.querydsl.core.types.Path;
import com.valentinakole.lms.exception.errors.IllegalAccessError;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class GenerateListsForQueryDsl {

    public <T> Map<String, List<?>> get(T entity, Map<String, Object> map, Map<String, Path<?>> paths) {

        List<Path<?>> key = new LinkedList<>();
        List<Object> value = new LinkedList<>();

        Class<?> clazz = entity.getClass();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            Field field = null;
            try {
                field = clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                value.add(field.get(entity));
            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {
                throw new IllegalAccessError(e.getMessage());
            }
            if (field != null) {
                key.add(paths.get(entry.getKey()));
            }
        }

        Map<String, List<?>> maps = new HashMap<>();
        maps.put("path", key);
        maps.put("value", value);

        return maps;
    }
}

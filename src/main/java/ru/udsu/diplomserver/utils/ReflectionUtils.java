package ru.udsu.diplomserver.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ReflectionUtils {

    @SneakyThrows
    public List<String> getAllStringParams(Object obj) {
        List<String> res = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.getType() == String.class) {
                field.setAccessible(true);
                res.add((String) field.get(obj));
            }
        }
        return res;
    }
}

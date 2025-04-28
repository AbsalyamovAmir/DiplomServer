package ru.udsu.diplomserver.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@UtilityClass
public class HashUtils {

    public Integer getHashFromStrings(List<String> args) {
        return args.stream()
                .filter(StringUtils::isNotEmpty)
                .map(String::hashCode)
                .mapToInt(Integer::intValue)
                .sum();
    }

}

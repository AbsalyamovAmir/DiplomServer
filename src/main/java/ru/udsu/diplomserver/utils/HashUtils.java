package ru.udsu.diplomserver.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
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

    @SneakyThrows
    public String getFileHash(File file) {
        return DigestUtils.sha256Hex(new FileInputStream(file));
    }

}

package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class FstecSourceDto {
    @XmlValue
    private String urls;

    public Set<String> getUrlArray() {
        if (urls == null) {
            return null;
        }

        return new HashSet<>(Arrays.asList(urls.split("\\s+")));
    }
}

package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class FstecSourceDto {
    @XmlValue
    private String urls;

    public String[] getUrlArray() {
        if (urls == null) {
            return null;
        }
        return urls.split("\\s+");
    }
}

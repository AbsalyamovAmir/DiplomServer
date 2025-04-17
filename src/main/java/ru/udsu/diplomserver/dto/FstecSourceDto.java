package ru.udsu.diplomserver.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class FstecSourceDto {
    @XmlElement(name = "url")
    private String url;

    @XmlElement(name = "description")
    private String description;
}

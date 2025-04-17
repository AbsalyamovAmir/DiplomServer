package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class FstecCweDto {
    @XmlElement(name = "identifier")
    private String identifier;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;
}

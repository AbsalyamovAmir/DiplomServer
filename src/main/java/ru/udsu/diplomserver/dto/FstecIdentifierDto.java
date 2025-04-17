package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class FstecIdentifierDto {
    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "value")
    private String value;

    @XmlElement(name = "link")
    private String link;
}

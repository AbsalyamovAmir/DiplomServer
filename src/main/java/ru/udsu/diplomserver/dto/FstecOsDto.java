package ru.udsu.diplomserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class FstecOsDto {
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "version")
    private String version;

    @XmlElement(name = "platform")
    private String platform;

    @XmlElement(name = "vendor")
    private String vendor;
}

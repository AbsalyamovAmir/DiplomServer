package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class FstecSoftwareDto {
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "version")
    private String version;

    @XmlElement(name = "platform")
    private String platform;

    @XmlElement(name = "vendor")
    private String vendor;

    @XmlElementWrapper(name = "types")
    @XmlElement(name = "type")
    private List<String> types;
}
package ru.udsu.diplomserver.dto.vulnerabilities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@NoArgsConstructor
@Setter
public class CvssVectorDto {
    @XmlAttribute
    private double score;

    @XmlValue
    private String value;
}

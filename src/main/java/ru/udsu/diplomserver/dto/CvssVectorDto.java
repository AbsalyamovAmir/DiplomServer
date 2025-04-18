package ru.udsu.diplomserver.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class CvssVectorDto {
    @XmlAttribute
    private double score;

    @XmlValue
    private String value;
}

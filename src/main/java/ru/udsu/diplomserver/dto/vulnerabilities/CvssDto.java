package ru.udsu.diplomserver.dto.vulnerabilities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CvssDto {
    @XmlElement(name = "vector")
    private CvssVectorDto vector;

    public String getVector() {
        return vector.getValue();
    }

    public double getScore() {
        return vector.getScore();
    }
}

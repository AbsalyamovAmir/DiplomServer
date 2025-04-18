package ru.udsu.diplomserver.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

import java.io.File;

@Service
public class FstecXmlParserService {
    //Для тестов, если с диска дергать
    public FstecVulnerabilityListDto parseVulnerabilitiesFromXml(String filePath) {
        try {
            File xmlFile = new File(filePath);

            if (!xmlFile.exists()) {
                throw new RuntimeException("Файл не найден: " + filePath);
            }

            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }

    public FstecVulnerabilityListDto parseVulnerabilities(File xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }
}

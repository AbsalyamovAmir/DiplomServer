package ru.udsu.diplomserver.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

import java.io.File;

@Service
public class FstecXmlParserService {
    public FstecVulnerabilityListDto parseVulnerabilitiesFromXml(String filePath) {
        try {
            File xmlFile = new File(filePath);

            // Добавляем валидацию на существование файла
            if (!xmlFile.exists()) {
                throw new RuntimeException("Файл не найден: " + filePath);
            }

            // Создаем контекст JAXB
            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            // Демаршаллинг (XML -> Object)
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }

    public FstecVulnerabilityListDto parseVulnerabilities(File xml) {
        try {
            // Создаем контекст JAXB
            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            // Демаршаллинг (XML -> Object)
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }
}

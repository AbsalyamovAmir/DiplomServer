package ru.udsu.diplomserver.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.service.FstecXmlParserService;

import java.io.File;

@Service
@Slf4j
public class FstecXmlParserServiceImpl implements FstecXmlParserService {
    //Для тестов, если с диска дергать
    public FstecVulnerabilityListDto parseVulnerabilitiesFromDisk(String filePath) {
        try {
            log.info("Start parseVulnerabilities from disk");
            File xmlFile = new File(filePath);

            if (!xmlFile.exists()) {
                throw new RuntimeException("Файл не найден: " + filePath);
            }

            log.info("Файл получен, сейчас начнется парсинг");

            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }

    public FstecVulnerabilityListDto parseVulnerabilities(File xml) {
        try {
            log.info("Start parseVulnerabilities from fstec");
            JAXBContext context = JAXBContext.newInstance(FstecVulnerabilityListDto.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (FstecVulnerabilityListDto) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге XML файла: " + e.getMessage(), e);
        }
    }
}

package ru.udsu.diplomserver;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

import java.io.File;

public class TestMain {

    public static void main(String[] args) throws JAXBException {

        File file = new File("D:\\projects\\projectfiles\\export\\export.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(FstecVulnerabilityListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        FstecVulnerabilityListDto vulnerability1 = (FstecVulnerabilityListDto) unmarshaller.unmarshal(file);
        System.out.println("ok");
    }

}

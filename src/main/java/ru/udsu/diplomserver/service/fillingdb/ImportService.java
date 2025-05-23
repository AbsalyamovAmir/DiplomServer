package ru.udsu.diplomserver.service.fillingdb;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface ImportService {

    void getAndSaveVulnerabilitiesFromFstec() throws IOException, JAXBException;
}

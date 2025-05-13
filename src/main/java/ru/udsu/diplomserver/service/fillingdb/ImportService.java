package ru.udsu.diplomserver.service.fillingdb;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface ImportService {

    int getAndSaveVulnerabilitiesFromFstec() throws IOException, JAXBException;

    int getAndSaveVulnerabilitiesFromDisk() throws IOException;
}

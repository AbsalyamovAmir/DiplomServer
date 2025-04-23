package ru.udsu.diplomserver.services;

import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

import java.io.File;

public interface FstecXmlParserService {
    FstecVulnerabilityListDto parseVulnerabilitiesFromDisk(String filePath);

    FstecVulnerabilityListDto parseVulnerabilities(File xml);
}

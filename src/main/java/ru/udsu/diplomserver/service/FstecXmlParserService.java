package ru.udsu.diplomserver.service;

import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

import java.io.File;

public interface FstecXmlParserService {
    FstecVulnerabilityListDto parseVulnerabilitiesFromDisk(String filePath);

    FstecVulnerabilityListDto parseVulnerabilities(File xml);
}

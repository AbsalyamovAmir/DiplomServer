package ru.udsu.diplomserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.services.DownloadService;
import ru.udsu.diplomserver.services.ZipService;
import ru.udsu.diplomserver.utility.VulnerabilityXmlParser;

import java.io.File;
import java.util.List;
import java.util.zip.ZipInputStream;

@RestController
@RequiredArgsConstructor
public class VulnController {

    private final DownloadService downloadService;

    private final ZipService zipService;

    private final VulnerabilityXmlParser xmlParser;

    // Запуск по HTTP-запросу
    @GetMapping("/parse")
    public List<Vulnerability> parseVuln() throws Exception {
        return processVulnData();
    }

    // Или автоматический запуск каждые 24 часа
    @Scheduled(fixedDelay = 86400000)
    public void autoParse() throws Exception {
        processVulnData();
    }

    private List<Vulnerability> processVulnData() throws Exception {
        try (ZipInputStream zipStream = downloadService.downloadZipFile()) {
            File xmlFile = zipService.extractXmlFromZip(zipStream);
            return xmlParser.parse(xmlFile.getAbsolutePath());
        }
    }
}

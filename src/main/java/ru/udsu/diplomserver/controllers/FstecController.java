package ru.udsu.diplomserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.services.DownloadService;
import ru.udsu.diplomserver.services.FstecXmlParserService;

@RestController
@RequestMapping("/api/fstec")
@RequiredArgsConstructor
public class FstecController {

    private final DownloadService downloadService;
    private final FstecXmlParserService fstecXmlParserService;

    @PostMapping("/import")
    public ResponseEntity<String> importVulnerabilities() {
        try {
            FstecVulnerabilityListDto result = fstecXmlParserService.parseVulnerabilities(downloadService.downloadZipFile());
            assert result != null;
            return ResponseEntity.ok("Найдено уязвимостей: " + result.getVulnerabilities().size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }
    }
}

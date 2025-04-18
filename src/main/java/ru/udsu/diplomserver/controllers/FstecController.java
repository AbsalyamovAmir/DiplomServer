package ru.udsu.diplomserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.services.DownloadService;
import ru.udsu.diplomserver.services.FstecXmlParserService;
import ru.udsu.diplomserver.utility.VulnerabilityMapper;

import java.util.List;

@RestController
@RequestMapping("/api/fstec")
@RequiredArgsConstructor
public class FstecController {

    private final DownloadService downloadService;
    private final FstecXmlParserService fstecXmlParserService;
    private final VulnerabilityMapper vulnerabilityMapper;

    @PostMapping("/import")
    public ResponseEntity<String> importVulnerabilities() {
        try {
            FstecVulnerabilityListDto result = fstecXmlParserService.parseVulnerabilities(downloadService.downloadAndExtractExportXml());
            assert result != null;
            List<Vulnerability> vulnerabilities = vulnerabilityMapper.fromFstecVulnerabilityListDtoToVulnerability(result);
            return ResponseEntity.ok("Найдено уязвимостей: " + vulnerabilities.size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            // Пример использования (для тестирования)
            FstecVulnerabilityListDto result = fstecXmlParserService.parseVulnerabilitiesFromXml("E:\\export.xml");
            vulnerabilityMapper.fromFstecVulnerabilityListDtoToVulnerability(result);
            return ResponseEntity.ok("Найдено уязвимостей: " + result.getVulnerabilities().size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }

    }
}

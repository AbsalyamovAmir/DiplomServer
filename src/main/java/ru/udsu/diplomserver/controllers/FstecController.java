package ru.udsu.diplomserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.services.ZipDownloaderService;

@RestController
@RequestMapping("/api/fstec")
@RequiredArgsConstructor
public class FstecController {

    private final ZipDownloaderService zipDownloaderService;

    @PostMapping("/import")
    public ResponseEntity<String> importVulnerabilities() {
        try {
            zipDownloaderService.downloadParseAndSave(
                    "https://bdu.fstec.ru/files/documents/vulxml.zip");
            return ResponseEntity.ok("Vulnerabilities imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }
    }
}

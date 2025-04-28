package ru.udsu.diplomserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.service.ImportService;

@RestController
@RequestMapping("/api/fstec")
@RequiredArgsConstructor
@Slf4j
public class FstecController {

    private final ImportService importService;

    @GetMapping("/import")
    public ResponseEntity<String> importVulnerabilities() {
        try {
            int result = importService.getAndSaveVulnerabilitiesFromFstec();
            return ResponseEntity.ok("Найдено уязвимостей: " + result);
        } catch (Exception e) {
            log.error("Что-то произошло", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            int result = importService.getAndSaveVulnerabilitiesFromDisk();

            log.info("Парсинг закончен. Начинаем маппинг");
            return ResponseEntity.ok("Найдено уязвимостей: " + result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during import: " + e.getMessage());
        }

    }
}

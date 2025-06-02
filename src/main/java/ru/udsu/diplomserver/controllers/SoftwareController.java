package ru.udsu.diplomserver.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.dto.installedsoftwares.SystemInformationResponse;
import ru.udsu.diplomserver.exceptions.InvalidDataException;
import ru.udsu.diplomserver.service.comparisonsoftwares.VulnerabilityCheckService;
import ru.udsu.diplomserver.service.parsesoftwares.CompressionService;
import ru.udsu.diplomserver.service.parsesoftwares.EncryptionService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SoftwareController {
    private final EncryptionService encryptionService;
    private final CompressionService compressionService;
    private final VulnerabilityCheckService vulnerabilityCheckService;
    private final ObjectMapper objectMapper;

    @PostMapping("/upload-report")
    public ResponseEntity<String> uploadReport(@RequestBody byte[] encryptedData) {
        log.info("Получен запрос на загрузку отчета. Размер данных: {} байт", encryptedData.length);

        try {
            String decryptedJson = processEncryptedData(encryptedData);
            log.info("Отчет успешно обработан. Длина расшифрованного содержимого: {} символов",
                    decryptedJson.length());
            SystemInformationResponse response = objectMapper.readValue(
                    decryptedJson,
                    new TypeReference<>() {}
            );
            return ResponseEntity.ok(vulnerabilityCheckService.checkInstalledSoftware(response));
        } catch (InvalidDataException e) {
            log.warn("Получены некорректные данные: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Ошибка обработки отчета: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Ошибка обработки");
        }
    }

    private String processEncryptedData(byte[] encryptedData) throws Exception {
        validateInputData(encryptedData);
        byte[] decryptedData = encryptionService.decrypt(encryptedData);
        return compressionService.decompress(decryptedData);
    }

    private void validateInputData(byte[] encryptedData) throws InvalidDataException {
        if (encryptedData.length < 28) {
            log.warn("Некорректная длина данных: {} байт (ожидалось минимум 28)", encryptedData.length);
            throw new InvalidDataException("Некорректная длина данных");
        }
    }
}

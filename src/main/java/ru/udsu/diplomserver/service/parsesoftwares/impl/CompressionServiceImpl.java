package ru.udsu.diplomserver.service.parsesoftwares.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.service.parsesoftwares.CompressionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

@Service
@Slf4j
public class CompressionServiceImpl implements CompressionService {

    public String decompress(byte[] compressedData) throws IOException {
        log.info("Распаковка данных размером {} байт", compressedData.length);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzip = new GZIPInputStream(bis)) {

            byte[] result = gzip.readAllBytes();
            log.info("Данные успешно распакованы. Размер: {} байт", result.length);

            return new String(result, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Ошибка распаковки данных: {}", e.getMessage(), e);
            throw e;
        }
    }
}

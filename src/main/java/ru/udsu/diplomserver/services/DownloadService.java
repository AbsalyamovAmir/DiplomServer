package ru.udsu.diplomserver.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class DownloadService {
    public File downloadZipFile() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://bdu.fstec.ru/files/documents/vulxml.zip";

        // Создаем временный файл
        File tempFile = File.createTempFile("vulxml", ".zip");

        // Удаляем файл при выходе из JVM (опционально)
        tempFile.deleteOnExit();
        log.info("Downloading vulxml.zip");
        // Скачиваем файл
        restTemplate.execute(
                URI.create(url),
                HttpMethod.GET,
                null,
                clientHttpResponse -> {
                    try (FileOutputStream out = new FileOutputStream(tempFile)) {
                        FileCopyUtils.copy(clientHttpResponse.getBody(), out);
                    }
                    return tempFile;
                }
        );
        log.info("Downloaded vulxml.zip");
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(tempFile))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (entry.getName().equals("export/export.xml")) {
                    // Создаем временный файл для export.xml
                    File exportXmlFile = File.createTempFile("export", ".xml");
                    exportXmlFile.deleteOnExit();

                    // Копируем содержимое entry в файл
                    try (FileOutputStream out = new FileOutputStream(exportXmlFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = zipIn.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                    return exportXmlFile; // Возвращаем найденный XML
                }
            }
        }
        log.info("Unzip vulxml.zip");
        throw new FileNotFoundException("Файл export/export.xml не найден в архиве!");
    }
}
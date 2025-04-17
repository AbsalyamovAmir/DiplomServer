package ru.udsu.diplomserver.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ZipDownloaderService {

    private final FstecXmlParserService fstecXmlParserService;

    public void downloadParseAndSave(String zipUrl) throws Exception {
        // 1. Скачиваем ZIP-файл
        File tempZipFile = File.createTempFile("vulxml", ".zip");
        FileUtils.copyURLToFile(new URL(zipUrl), tempZipFile);

        // 2. Распаковываем ZIP и ищем XML
        try (ZipArchiveInputStream zipInput = new ZipArchiveInputStream(
                new BufferedInputStream(new FileInputStream(tempZipFile)))) {

            ZipArchiveEntry entry;
            while ((entry = zipInput.getNextZipEntry()) != null) {
                if (entry.getName().equals("export/export.xml") ||
                        entry.getName().equals("export.xml")) {

                    // Создаем временный файл для XML
                    File xmlFile = File.createTempFile("export", ".xml");
                    try (OutputStream outputStream = new FileOutputStream(xmlFile)) {
                        IOUtils.copy(zipInput, outputStream);
                    }

                    // 3. Парсим XML и сохраняем в базу
                    fstecXmlParserService.parseVulnerabilities(xmlFile);
//                    parserService.parseAndSaveVulnerabilities(xmlFile);

                    // Удаляем временный файл
                    xmlFile.delete();
                    break;
                }
            }
        } finally {
            // Удаляем временный ZIP-файл
            tempZipFile.delete();
        }
    }
}

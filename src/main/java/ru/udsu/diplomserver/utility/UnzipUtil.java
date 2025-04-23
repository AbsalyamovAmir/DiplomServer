package ru.udsu.diplomserver.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
@Slf4j
public class UnzipUtil {

    public File unzip(File zipFile) throws IOException {
        log.info("Unzipping {}", zipFile.getAbsolutePath());
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
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
                    log.info("Unzipping export file {}", exportXmlFile.getAbsolutePath());
                    return exportXmlFile; // Возвращаем найденный XML
                }
            }
        }

        throw new FileNotFoundException("Файл export/export.xml не найден в архиве!");
    }
}

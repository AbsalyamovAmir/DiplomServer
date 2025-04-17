package ru.udsu.diplomserver.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipService {
    public File extractXmlFromZip(InputStream zipInputStream) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipInputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith("export.xml")) {
                    File tempFile = File.createTempFile("export", ".xml");
                    Files.copy(zis, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    return tempFile;
                }
            }
            throw new IOException("export.xml not found in ZIP");
        }
    }

}

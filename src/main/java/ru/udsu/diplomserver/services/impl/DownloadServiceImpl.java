package ru.udsu.diplomserver.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import ru.udsu.diplomserver.services.DownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {

    @Value("${fstec.url}")
    private String fstecUrl;

    public File downloadFromFstec() throws IOException {
        log.info("Downloading from fstec");
        RestTemplate restTemplate = new RestTemplate();
        String url = fstecUrl;

        File tempZipFile = File.createTempFile("vulxml", ".zip");
        tempZipFile.deleteOnExit();

        restTemplate.execute(
                URI.create(url),
                HttpMethod.GET,
                null,
                response -> {
                    try (InputStream in = response.getBody();
                         FileOutputStream out = new FileOutputStream(tempZipFile)) {
                        FileCopyUtils.copy(in, out);
                    }
                    return tempZipFile;
                }
        );
        log.info("Download completed");
        return tempZipFile;
    }
}
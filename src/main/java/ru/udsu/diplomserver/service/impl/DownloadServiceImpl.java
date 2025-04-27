package ru.udsu.diplomserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import ru.udsu.diplomserver.service.DownloadService;

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

    @Value("${fstec.retry.count}")
    private Integer retryCount;

    private int retryNow = 0;

    @Override
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

    private boolean needRetry() {
        if (retryCount == null) {
            return false;
        }
        return retryNow < retryCount;
    }

}
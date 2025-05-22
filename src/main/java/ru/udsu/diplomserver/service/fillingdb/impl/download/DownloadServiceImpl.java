package ru.udsu.diplomserver.service.fillingdb.impl.download;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import ru.udsu.diplomserver.utils.UnzipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final RestTemplate restTemplate;

    @Value("${fstec.url}")
    private String fstecUrl;

    @Override
    @Retryable(
            maxAttemptsExpression = "${fstec.retry.count:3}",
            backoff = @Backoff(delayExpression = "${fstec.retry.delay:1000}")
    )
    public File downloadFromFstec() throws IOException {
        log.info("Downloading from fstec");

        File tempZipFile = File.createTempFile("vulxml", ".zip");
        tempZipFile.deleteOnExit();

        return restTemplate.execute(
                URI.create(fstecUrl),
                HttpMethod.GET,
                null,
                response -> {
                    try (InputStream in = response.getBody();
                         FileOutputStream out = new FileOutputStream(tempZipFile)) {
                        FileCopyUtils.copy(in, out);
                    }
                    return UnzipUtil.unzip(tempZipFile);
                }
        );
    }
}
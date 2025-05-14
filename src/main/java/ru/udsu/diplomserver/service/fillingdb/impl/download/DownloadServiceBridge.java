package ru.udsu.diplomserver.service.fillingdb.impl.download;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Primary
@Service
@RequiredArgsConstructor
public class DownloadServiceBridge implements DownloadService {

    @Value("${fstec.stub}")
    private Boolean isStubActive;

    private final DownloadServiceStub downloadServiceStub;
    private final DownloadServiceImpl downloadServiceImpl;

    @Override
    public File downloadFromFstec() throws IOException {
        if (Boolean.TRUE.equals(isStubActive)) {
            return downloadServiceStub.downloadFromFstec();
        }
        return downloadServiceImpl.downloadFromFstec();
    }
}

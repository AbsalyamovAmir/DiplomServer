package ru.udsu.diplomserver.service.fillingdb.impl.download;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DownloadServiceStub implements DownloadService {

    @Value("${fstec.file.path}")
    private String filePath;

    @Override
    public File downloadFromFstec() throws IOException {
        return new File(filePath);
    }
}

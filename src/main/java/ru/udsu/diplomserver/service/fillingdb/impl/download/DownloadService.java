package ru.udsu.diplomserver.service.fillingdb.impl.download;

import java.io.File;
import java.io.IOException;

public interface DownloadService {
    File downloadFromFstec() throws IOException;
}

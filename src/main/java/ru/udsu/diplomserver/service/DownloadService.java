package ru.udsu.diplomserver.service;

import java.io.File;
import java.io.IOException;

public interface DownloadService {
    File downloadFromFstec() throws IOException;
}

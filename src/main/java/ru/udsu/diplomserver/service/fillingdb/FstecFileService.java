package ru.udsu.diplomserver.service.fillingdb;

import ru.udsu.diplomserver.models.FstecFile;

public interface FstecFileService {

    FstecFile getFstecFile();

    void updateHashFile(String hash);

}

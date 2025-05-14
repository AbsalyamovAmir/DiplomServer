package ru.udsu.diplomserver.service.fillingdb.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.models.FstecFile;
import ru.udsu.diplomserver.repositories.FstecFileRepository;
import ru.udsu.diplomserver.service.fillingdb.FstecFileService;

@Service
@RequiredArgsConstructor
public class FstecFileServiceImpl implements FstecFileService {


    private final FstecFileRepository repository;

    @Override
    public FstecFile getFstecFile() {
        return repository.getFile();
    }

    @Override
    public void updateHashFile(String hash) {
        FstecFile file = getFstecFile();
        file.setFileHash(hash);
        repository.save(file);
    }
}

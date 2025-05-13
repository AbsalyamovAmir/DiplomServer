package ru.udsu.diplomserver.service.fillingdb.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.dto.vulnerabilities.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.mapper.VulnerabilityMapper;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.service.fillingdb.DownloadService;
import ru.udsu.diplomserver.service.fillingdb.ImportService;
import ru.udsu.diplomserver.service.fillingdb.VulnerabilityService;
import ru.udsu.diplomserver.utils.UnzipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {

    private final DownloadService downloadService;
    private final VulnerabilityMapper vulnerabilityMapper;
    private final VulnerabilityService vulnerabilityService;

    @Value("${import.file.path}")
    private String filePath;

    @Override
    public int getAndSaveVulnerabilitiesFromFstec() throws IOException, JAXBException {
        File file = UnzipUtil.unzip(downloadService.downloadFromFstec());
        log.info("Файл скачан и расархивирован");
        FstecVulnerabilityListDto fstecVulnerabilityListDto = getFstecVulnerabilitiesFromFile(file);
        log.info("Парсинг окончен");
        int size = mapAndSaveVul(fstecVulnerabilityListDto);
        log.info("Сохраненеие в бд закончено");

        return size;
    }

    @Override
    public int getAndSaveVulnerabilitiesFromDisk() {
        int size = 0;
        try {
            File file = new File(filePath);
            FstecVulnerabilityListDto fstecVulnerabilityListDto = getFstecVulnerabilitiesFromFile(file);
            size = mapAndSaveVul(fstecVulnerabilityListDto);
        } catch (Exception exception) {
            exception.getMessage();
        }
        return size;
    }

    private int mapAndSaveVul(FstecVulnerabilityListDto fstecVulnerabilityListDto) {
        List<Vulnerability> vulList = new ArrayList<>();
        Optional.ofNullable(fstecVulnerabilityListDto)
                .map(FstecVulnerabilityListDto::getVulnerabilities)
                .stream()
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .forEach(vul -> vulList.add(vulnerabilityMapper.mapToEntity(vul)));

        vulnerabilityService.checkAndSaveVul(vulList);
        return vulList.size();
    }

    private FstecVulnerabilityListDto getFstecVulnerabilitiesFromFile(File file) throws JAXBException {
        log.info("Парсинг xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(FstecVulnerabilityListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (FstecVulnerabilityListDto) unmarshaller.unmarshal(file);
    }
}

package ru.udsu.diplomserver.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.mapper.VulnerabilityMapper;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.service.DownloadService;
import ru.udsu.diplomserver.service.ImportService;
import ru.udsu.diplomserver.service.VulnerabilityService;
import ru.udsu.diplomserver.utility.UnzipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final DownloadService downloadService;
    private final VulnerabilityMapper vulnerabilityMapper;
    private final VulnerabilityService vulnerabilityService;

    @Value("${import.file.path}")
    private String filePath;

    @Override
    public int getAndSaveVulnerabilitiesFromFstec() throws IOException, JAXBException {
        File file = UnzipUtil.unzip(downloadService.downloadFromFstec());
        FstecVulnerabilityListDto fstecVulnerabilityListDto = getFstecVulnerabilitiesFromFile(file);
        List<Vulnerability> vulnerabilities = fstecVulnerabilityListDto.getVulnerabilities().stream().map(vulnerabilityMapper::mapToEntity).toList();

        return vulnerabilities.size();
    }

    @Override
    public int getAndSaveVulnerabilitiesFromDisk() {
        try {
            File file = new File(filePath);
            FstecVulnerabilityListDto fstecVulnerabilityListDto = getFstecVulnerabilitiesFromFile(file);
            List<Vulnerability> vulList = new ArrayList<>();
            Optional.ofNullable(fstecVulnerabilityListDto)
                    .map(FstecVulnerabilityListDto::getVulnerabilities)
                    .stream()
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .forEach(vul -> vulList.add(vulnerabilityMapper.mapToEntity(vul)));

            vulnerabilityService.checkAndSaveVul(vulList);
        } catch (Exception exception) {
            exception.getMessage();
        }

        return 1;
    }

    private FstecVulnerabilityListDto getFstecVulnerabilitiesFromFile(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(FstecVulnerabilityListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (FstecVulnerabilityListDto) unmarshaller.unmarshal(file);
    }
}

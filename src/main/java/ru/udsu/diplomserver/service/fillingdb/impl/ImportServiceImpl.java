package ru.udsu.diplomserver.service.fillingdb.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.udsu.diplomserver.dto.vulnerabilities.FstecVulnerabilityDto;
import ru.udsu.diplomserver.dto.vulnerabilities.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.mapper.VulnerabilityMapper;
import ru.udsu.diplomserver.models.FstecFile;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.service.fillingdb.FstecFileService;
import ru.udsu.diplomserver.service.fillingdb.impl.download.DownloadService;
import ru.udsu.diplomserver.service.fillingdb.ImportService;
import ru.udsu.diplomserver.service.fillingdb.VulnerabilityService;
import ru.udsu.diplomserver.utils.HashUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {

    private final DownloadService downloadService;
    private final VulnerabilityMapper vulnerabilityMapper;
    private final VulnerabilityService vulnerabilityService;
    private final FstecFileService fstecFileService;

    @Value("${fstec.file.path}")
    private String filePath;

    @SneakyThrows
    @Override
    public int getAndSaveVulnerabilitiesFromFstec() {

        File file = downloadService.downloadFromFstec();
        log.info("Файл скачан и разархивирован");
        String hash = HashUtils.getFileHash(file);
        if (needUpdate(hash)) {
            return 0;
        }

        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(FstecVulnerabilityDto.class);

                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(file));

                List<FstecVulnerabilityDto> batch = new ArrayList<>();
                int batchCount = 1000;
                while (reader.hasNext()) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT && "vul".equals(reader.getLocalName())) {
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        FstecVulnerabilityDto dto = (FstecVulnerabilityDto) unmarshaller.unmarshal(reader);
                        batch.add(dto);
                        if (batch.size() >= batchCount) {
                            List<FstecVulnerabilityDto> items = new ArrayList<>(batch);
                            executorService.submit(() -> mapAndSaveVul(items));
                            batch.clear();
                        }
                    }
                }

                if (!batch.isEmpty()) {
                    executorService.submit(() -> mapAndSaveVul(batch));
                }
                fstecFileService.updateHashFile(hash);
                return 0;
            } catch (Exception exception) {
                log.error(exception.getMessage());
            }
            fstecFileService.updateHashFile(hash);
        }

        return 0;
    }

    @Override
    public int getAndSaveVulnerabilitiesFromDisk() {
        int size = 0;
        try {
            File file = new File(filePath);
            FstecVulnerabilityListDto fstecVulnerabilityListDto = getFstecVulnerabilitiesFromFile(file);
//            size = mapAndSaveVul(fstecVulnerabilityListDto);
        } catch (Exception exception) {
            exception.getMessage();
        }
        return size;
    }

    private int mapAndSaveVul(List<FstecVulnerabilityDto> dto) {
        if (CollectionUtils.isEmpty(dto)) {
            return 0;
        }
        List<Vulnerability> vulList = new ArrayList<>();
        dto.stream()
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

    private boolean needUpdate(String hash) {
        FstecFile file = fstecFileService.getFstecFile();
        return Objects.equals(file.getFileHash(), hash);
    }

}

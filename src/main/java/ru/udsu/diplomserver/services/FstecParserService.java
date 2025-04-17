package ru.udsu.diplomserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.udsu.diplomserver.dto.*;
import ru.udsu.diplomserver.models.*;
import ru.udsu.diplomserver.repositories.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FstecParserService {

    private final VulnerabilityRepository vulnerabilityRepository;
    private final CweRepository cweRepository;
    private final SoftwareRepository softwareRepository;
    private final OperatingSystemRepository osRepository;
    private final SourceRepository sourceRepository;
    private final IdentifierRepository identifierRepository;
    private final VendorRepository vendorRepository;
    private final SoftwareTypeRepository softwareTypeRepository;

    @Transactional
    public void parseAndSaveVulnerabilities(File xmlFile) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(FstecVulnerabilityDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        FstecVulnerabilityDto dto = (FstecVulnerabilityDto) jaxbUnmarshaller.unmarshal(xmlFile);

        // Маппинг и сохранение уязвимости
        Vulnerability vulnerability = mapToVulnerability(dto);
        vulnerabilityRepository.save(vulnerability);
    }

    private Vulnerability mapToVulnerability(FstecVulnerabilityDto dto) {
        Vulnerability vulnerability = new Vulnerability();
        vulnerability.setIdentifier(dto.getIdentifier());
        vulnerability.setName(dto.getName());
        vulnerability.setDescription(dto.getDescription());

        try {
            if (dto.getIdentifyDate() != null) {
                vulnerability.setIdentifyDate(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getIdentifyDate()));
            }
        } catch (Exception e) {
            // Обработка ошибки парсинга даты
        }

        vulnerability.setCvssVector(dto.getCvssVector());
        vulnerability.setCvssScore(dto.getCvssScore());
        vulnerability.setSeverity(dto.getSeverity());
        vulnerability.setSolution(dto.getSolution());
        vulnerability.setVulStatus(dto.getVulStatus());
        vulnerability.setExploitStatus(dto.getExploitStatus());
        vulnerability.setFixStatus(dto.getFixStatus());
        vulnerability.setVulIncident(dto.getVulIncident());
        vulnerability.setVulClass(dto.getVulClass());
        vulnerability.setVulState(dto.getVulState());

        // Маппинг связанных сущностей
        if (dto.getCweEntries() != null) {
            vulnerability.setCweEntries(dto.getCweEntries().stream()
                    .map(this::mapToCwe)
                    .collect(Collectors.toSet()));
        }

        if (dto.getAffectedSoftware() != null) {
            vulnerability.setAffectedSoftware(dto.getAffectedSoftware().stream()
                    .map(this::mapToSoftware)
                    .collect(Collectors.toSet()));
        }

        if (dto.getAffectedOs() != null) {
            vulnerability.setAffectedOperatingSystems(dto.getAffectedOs().stream()
                    .map(this::mapToOperatingSystem)
                    .collect(Collectors.toSet()));
        }

        if (dto.getSources() != null) {
            vulnerability.setSources(dto.getSources().stream()
                    .map(this::mapToSource)
                    .collect(Collectors.toSet()));
        }

        if (dto.getIdentifiers() != null) {
            vulnerability.setIdentifiers(dto.getIdentifiers().stream()
                    .map(this::mapToIdentifier)
                    .collect(Collectors.toSet()));
        }

        return vulnerability;
    }

    private CWE mapToCwe(FstecCweDto dto) {
        // Поиск существующего CWE или создание нового
        return cweRepository.findByIdentifier(dto.getIdentifier())
                .orElseGet(() -> {
                    CWE cwe = new CWE();
                    cwe.setIdentifier(dto.getIdentifier());
                    cwe.setName(dto.getName());
                    cwe.setDescription(dto.getDescription());
                    return cweRepository.save(cwe);
                });
    }

    private Software mapToSoftware(FstecSoftwareDto dto) {
        // Поиск существующего ПО или создание нового
        return softwareRepository.findByNameAndVersion(dto.getName(), dto.getVersion())
                .orElseGet(() -> {
                    Software software = new Software();
                    software.setName(dto.getName());
                    software.setVersion(dto.getVersion());
                    software.setPlatform(dto.getPlatform());

                    // Маппинг производителя
                    if (dto.getVendor() != null) {
                        Vendor vendor = vendorRepository.findByName(dto.getVendor())
                                .orElseGet(() -> {
                                    Vendor newVendor = new Vendor();
                                    newVendor.setName(dto.getVendor());
                                    return vendorRepository.save(newVendor);
                                });
                        software.setVendor(vendor);
                    }

                    // Маппинг типов ПО
                    if (dto.getTypes() != null) {
                        software.setTypes(dto.getTypes().stream()
                                .map(typeName -> softwareTypeRepository.findByName(typeName)
                                        .orElseGet(() -> {
                                            SoftwareType type = new SoftwareType();
                                            type.setName(typeName);
                                            return softwareTypeRepository.save(type);
                                        }))
                                .collect(Collectors.toSet()));
                    }

                    return softwareRepository.save(software);
                });
    }

    private OperatingSystem mapToOperatingSystem(FstecOsDto dto) {
        // Сначала проверяем существование без создания объекта
        Optional<OperatingSystem> existingOs = osRepository.findByNameAndVersion(dto.getName(), dto.getVersion());
        if (existingOs.isPresent()) {
            return existingOs.get();
        }

        OperatingSystem os = new OperatingSystem();
        os.setName(dto.getName());
        os.setVersion(dto.getVersion());
        os.setPlatform(dto.getPlatform());

        if (dto.getVendor() != null && !dto.getVendor().isEmpty()) {
            Optional<Vendor> vendor = vendorRepository.findByName(dto.getVendor());
            if (vendor.isPresent()) {
                os.setVendor(vendor.get());
            } else {
                Vendor newVendor = new Vendor();
                newVendor.setName(dto.getVendor());
                os.setVendor(vendorRepository.save(newVendor));
            }
        }

        return osRepository.save(os);
    }

    private Source mapToSource(FstecSourceDto dto) {
        Optional<Source> existingSource = sourceRepository.findByUrl(dto.getUrl());
        if (existingSource.isPresent()) {
            return existingSource.get();
        }

        Source source = new Source();
        source.setUrl(dto.getUrl());
        source.setDescription(dto.getDescription() != null ?
                dto.getDescription() : "Источник из FSTEC");

        return sourceRepository.save(source);
    }

    private Identifier mapToIdentifier(FstecIdentifierDto dto) {
        Optional<Identifier> existingIdentifier = identifierRepository.findByTypeAndValue(
                dto.getType(), dto.getValue());
        if (existingIdentifier.isPresent()) {
            return existingIdentifier.get();
        }

        Identifier identifier = new Identifier();
        identifier.setType(dto.getType());
        identifier.setValue(dto.getValue());
        identifier.setLink(dto.getLink() != null ?
                dto.getLink() : generateDefaultLink(dto.getType(), dto.getValue()));

        return identifierRepository.save(identifier);
    }

    private String generateDefaultLink(String type, String value) {
        switch (type.toUpperCase()) {
            case "CVE":
                return "https://nvd.nist.gov/vuln/detail/" + value;
            case "BID":
                return "https://www.securityfocus.com/bid/" + value;
            default:
                return null;
        }
    }
}
package ru.udsu.diplomserver.dto.installedsoftwares;

import lombok.Data;

import java.util.List;

@Data
public class VulnerableSoftwareReportDto {
    private InstalledSoftwareDto software;
    private List<VulnerabilityInfoDto> vulnerabilities;
}

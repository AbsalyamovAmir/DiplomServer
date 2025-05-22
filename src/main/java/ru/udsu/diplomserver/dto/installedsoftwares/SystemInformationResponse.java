package ru.udsu.diplomserver.dto.installedsoftwares;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SystemInformationResponse {
    @JsonProperty("SystemInfo")
    private SystemInfoDto systemInfo;

    @JsonProperty("InstalledSoftware")
    private List<InstalledSoftwareDto> installedSoftware;

}
package ru.udsu.diplomserver.dto.installedsoftwares;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstalledSoftwareDto {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("Vendor")
    private String vendor;
}

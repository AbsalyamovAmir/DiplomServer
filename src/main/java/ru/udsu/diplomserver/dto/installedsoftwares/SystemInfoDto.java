package ru.udsu.diplomserver.dto.installedsoftwares;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SystemInfoDto {
    @JsonProperty("OSName")
    private String OSName;
    @JsonProperty("MachineName")
    private String MachineName;
    @JsonProperty("OSVersion")
    private String OSVersion;
    @JsonProperty("BitOS")
    private String BitOS;
}

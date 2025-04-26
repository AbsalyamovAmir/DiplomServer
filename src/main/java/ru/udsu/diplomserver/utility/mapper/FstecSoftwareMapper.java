package ru.udsu.diplomserver.utility.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.udsu.diplomserver.dto.FstecSoftwareDto;
import ru.udsu.diplomserver.models.SoftwareType;
import ru.udsu.diplomserver.models.VulnerableSoftware;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FstecSoftwareMapper {

    @Mapping(target = "types", source = "types", qualifiedByName = "softwareDtoToVulnerableSoftware")
    VulnerableSoftware toEntity(FstecSoftwareDto dto);

    @Mapping(target = "types", source = "types", qualifiedByName = "vulnerableSoftwareToSoftwareDto")
    FstecSoftwareDto toDto(VulnerableSoftware entity);

    @Named("softwareDtoToVulnerableSoftware")
    default List<SoftwareType> softwareDtoToVulnerableSoftware(List<String> softwareTypes) {
        List<SoftwareType> types = new ArrayList<>();
        softwareTypes.forEach(type -> types.add(SoftwareType.builder().type(type).build()));
        return types;
    }

    @Named("vulnerableSoftwareToSoftwareDto")
    default List<String> vulnerableSoftwareToSoftwareDto(List<SoftwareType> softwareTypes) {
        List<String> types = new ArrayList<>();
        softwareTypes.forEach(type -> {
            types.add(type.getType());
        });
        return types;
    }
}

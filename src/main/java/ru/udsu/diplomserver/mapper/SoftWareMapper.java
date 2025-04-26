package ru.udsu.diplomserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.udsu.diplomserver.dto.FstecSoftwareDto;
import ru.udsu.diplomserver.models.Software;

@Mapper
public interface SoftWareMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "version", target = "version")
    @Mapping(source = "platform", target = "platform")
    Software mapToEntity(FstecSoftwareDto dto);

}

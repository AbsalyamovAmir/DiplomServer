package ru.udsu.diplomserver.utility.mapper;

import org.mapstruct.Mapper;
import ru.udsu.diplomserver.dto.FstecOsDto;
import ru.udsu.diplomserver.models.OperatingSystem;

@Mapper(componentModel = "spring")
public interface FstecOsMapper {

    OperatingSystem toEntity(FstecOsDto dto);

    FstecOsDto toDto(OperatingSystem entity);
}

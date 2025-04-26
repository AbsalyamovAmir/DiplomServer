package ru.udsu.diplomserver.utility.mapper;

import org.mapstruct.Mapper;
import ru.udsu.diplomserver.dto.FstecSourceDto;
import ru.udsu.diplomserver.models.Source;

@Mapper(componentModel = "spring")
public interface FstecSourceMapper {

    Source toEntity(FstecSourceDto dto);

    FstecSourceDto toDto(Source entity);
}

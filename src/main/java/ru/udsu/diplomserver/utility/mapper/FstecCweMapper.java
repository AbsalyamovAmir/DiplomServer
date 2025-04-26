package ru.udsu.diplomserver.utility.mapper;

import org.mapstruct.Mapper;
import ru.udsu.diplomserver.dto.FstecCweDto;
import ru.udsu.diplomserver.models.Cwe;

@Mapper(componentModel = "spring")
public interface FstecCweMapper {

    Cwe toEntity(FstecCweDto dto);

    FstecCweDto toDto(Cwe entity);
}

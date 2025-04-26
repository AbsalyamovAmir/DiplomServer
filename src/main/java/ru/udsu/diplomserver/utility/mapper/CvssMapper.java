package ru.udsu.diplomserver.utility.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.udsu.diplomserver.dto.CvssDto;
import ru.udsu.diplomserver.dto.CvssVectorDto;
import ru.udsu.diplomserver.models.Cvss;

@Mapper(componentModel = "spring")
public interface CvssMapper {

    @Mapping(target = "", qualifiedByName = "toCvssVectorDto")
    Cvss toEntity(CvssDto dto);

    CvssDto toDto(Cvss entity);

    default CvssVectorDto toCvssVectorDto(Cvss cvss) {
        if (cvss == null || cvss.getVector() == null) {
            return null;
        }
        CvssVectorDto vectorDto = new CvssVectorDto();
        vectorDto.setScore(cvss.getScore());
        vectorDto.setValue(cvss.getVector());
        return vectorDto;
    }
}

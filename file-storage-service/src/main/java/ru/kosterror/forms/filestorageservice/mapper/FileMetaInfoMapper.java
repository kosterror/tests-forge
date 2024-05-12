package ru.kosterror.forms.filestorageservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.filestorageservice.dto.FileMetaInfoDto;
import ru.kosterror.forms.filestorageservice.entity.FileMetaInfoEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileMetaInfoMapper {

    FileMetaInfoDto toDto(FileMetaInfoEntity entity);

}

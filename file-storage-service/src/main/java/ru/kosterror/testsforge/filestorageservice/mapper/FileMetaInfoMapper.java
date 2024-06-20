package ru.kosterror.testsforge.filestorageservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;
import ru.kosterror.testsforge.filestorageservice.entity.FileMetaInfoEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileMetaInfoMapper {

    FileMetaInfoDto toDto(FileMetaInfoEntity entity);

}

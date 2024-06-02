package ru.kosterror.forms.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.coreservice.dto.subject.CreateUpdateSubjectDto;
import ru.kosterror.forms.coreservice.dto.subject.SubjectDto;
import ru.kosterror.forms.coreservice.entity.subject.SubjectEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface SubjectMapper {

    SubjectEntity toEntity(CreateUpdateSubjectDto subjectDto);

    SubjectDto toDto(SubjectEntity entity);
}

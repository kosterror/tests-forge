package ru.kosterror.forms.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.userservice.dto.GroupDto;
import ru.kosterror.forms.userservice.dto.NewGroupDto;
import ru.kosterror.forms.userservice.entity.GroupEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMapper {

    GroupEntity toEntity(NewGroupDto dto);

    GroupDto toDto(GroupEntity entity);

}

package ru.kosterror.forms.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.commonmodel.user.GroupDto;
import ru.kosterror.forms.userservice.dto.UpdateGroupDto;
import ru.kosterror.forms.userservice.entity.GroupEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMapper {

    GroupEntity toEntity(UpdateGroupDto dto);

    GroupDto toDto(GroupEntity entity);

}

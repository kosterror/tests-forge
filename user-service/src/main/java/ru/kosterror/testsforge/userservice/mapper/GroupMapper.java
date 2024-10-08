package ru.kosterror.testsforge.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.commonmodel.user.GroupDto;
import ru.kosterror.testsforge.userservice.dto.UpdateGroupDto;
import ru.kosterror.testsforge.userservice.entity.GroupEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMapper {

    GroupEntity toEntity(UpdateGroupDto dto);

    GroupDto toDto(GroupEntity entity);

}

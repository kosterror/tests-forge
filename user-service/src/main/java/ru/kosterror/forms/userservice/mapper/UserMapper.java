package ru.kosterror.forms.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.commonmodel.user.UserDto;
import ru.kosterror.forms.userservice.dto.UpdateUserDto;
import ru.kosterror.forms.userservice.entity.UserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UpdateUserDto dto);

}

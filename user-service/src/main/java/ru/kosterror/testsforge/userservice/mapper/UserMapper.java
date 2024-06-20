package ru.kosterror.testsforge.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.userservice.dto.UpdateUserDto;
import ru.kosterror.testsforge.userservice.entity.UserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UpdateUserDto dto);

}

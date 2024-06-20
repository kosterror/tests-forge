package ru.kosterror.testsforge.mailservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;
import ru.kosterror.testsforge.mailservice.entity.MailDetailsEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MailDetailsMapper {

    MailDetailsEntity toEntity(SendMailDto dto);

}

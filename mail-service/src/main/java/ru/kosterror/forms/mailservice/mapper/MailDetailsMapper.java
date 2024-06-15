package ru.kosterror.forms.mailservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.forms.commonmodel.mail.SendMailDto;
import ru.kosterror.forms.mailservice.entity.MailDetailsEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MailDetailsMapper {

    MailDetailsEntity toEntity(SendMailDto dto);

}

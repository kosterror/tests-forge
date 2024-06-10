package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.formpattern.createupdate.CreateFormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;

import java.util.UUID;

public interface FormPatternService {

    FormPatternDto createFormPattern(UUID userId, CreateFormPatternDto createFormPatternDto);

    FormPatternDto getFormPattern(UUID id);

}

package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateFormPatternDto;

import java.util.UUID;

public interface FormPatternService {

    FormPatternDto createFormPattern(UUID userId, UpdateFormPatternDto updateFormPatternDto);

    FormPatternDto getFormPattern(UUID id);

}

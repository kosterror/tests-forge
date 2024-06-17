package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BaseFormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.forms.coreservice.entity.form.FormPatternEntity;

import java.util.UUID;

public interface FormPatternService {

    FormPatternDto createFormPattern(UUID userId, UpdateFormPatternDto updateFormPatternDto);

    FormPatternDto getFormPattern(UUID id);

    FormPatternEntity getFormPatternEntity(UUID id);

    PaginationResponse<BaseFormPatternDto> getFormPatterns(int page,
                                                           int size,
                                                           String name,
                                                           UUID ownerId,
                                                           UUID subjectId
    );
}

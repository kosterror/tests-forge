package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.formpattern.full.BaseFormPatternDto;
import ru.kosterror.testsforge.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.testsforge.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.testsforge.coreservice.entity.form.FormPatternEntity;

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

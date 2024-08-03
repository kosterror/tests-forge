package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.TestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

import java.util.UUID;

public interface TestPatternService {

    TestPatternDto createFormPattern(UUID userId, UpdateTestPatternDto updateTestPatternDto);

    TestPatternDto getFormPattern(UUID id);

    TestPatternEntity getFormPatternEntity(UUID id);

    PaginationResponse<BaseTestPatternDto> getFormPatterns(int page,
                                                           int size,
                                                           String name,
                                                           UUID ownerId,
                                                           UUID subjectId
    );
}

package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.testpattern.full.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.testpattern.full.TestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.testpattern.update.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity;

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

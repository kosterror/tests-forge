package ru.kosterror.testsforge.coreservice.service.test;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.TestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

import java.util.UUID;

public interface TestPatternService {

    TestPatternDto createTestPattern(UUID userId, UpdateTestPatternDto updateTestPatternDto);

    TestPatternDto getTestPattern(UUID id);

    TestPatternEntity getTestPatternEntity(UUID id);

    PaginationResponse<BaseTestPatternDto> getTestPatterns(int page,
                                                           int size,
                                                           String name,
                                                           UUID ownerId,
                                                           UUID subjectId
    );
}

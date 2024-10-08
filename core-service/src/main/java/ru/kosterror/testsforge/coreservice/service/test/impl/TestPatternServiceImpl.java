package ru.kosterror.testsforge.coreservice.service.test.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.NewTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.TestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.TestPatternFactory;
import ru.kosterror.testsforge.coreservice.mapper.TestPatternMapper;
import ru.kosterror.testsforge.coreservice.repository.TestPatternRepository;
import ru.kosterror.testsforge.coreservice.service.attachment.AttachmentService;
import ru.kosterror.testsforge.coreservice.service.test.TestPatternService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.TestPatternSpecification.*;

@Service
@RequiredArgsConstructor
public class TestPatternServiceImpl implements TestPatternService {

    private final TestPatternRepository testPatternRepository;
    private final TestPatternMapper testPatternMapper;
    private final TestPatternFactory testPatternFactory;
    private final AttachmentService attachmentService;

    @Override
    @Transactional
    public TestPatternDto createTestPattern(UUID userId, NewTestPatternDto newTestPatternDto) {
        var testPattern = testPatternFactory.buildTestPatternEntity(newTestPatternDto);
        attachmentService.validateAttachments(testPattern);

        testPattern = testPatternRepository.save(testPattern);

        return testPatternMapper.toDto(testPattern);
    }

    @Override
    public TestPatternDto getTestPattern(UUID id) {
        var testPattern = getTestPatternEntity(id);

        return testPatternMapper.toDto(testPattern);
    }

    @Override
    public TestPatternEntity getTestPatternEntity(UUID id) {
        return testPatternRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test pattern with id %s not found".formatted(id)));
    }

    @Override
    public PaginationResponse<BaseTestPatternDto> getTestPatterns(int page,
                                                                  int size,
                                                                  String name,
                                                                  UUID ownerId,
                                                                  UUID subjectId
    ) {
        var specification = Specification.<TestPatternEntity>where(null)
                .and(hasNameLike(name))
                .and(hasOwner(ownerId))
                .and(hasSubject(subjectId))
                .and(orderByName());

        var testPage = testPatternRepository.findAll(specification, PageRequest.of(page, size));

        var testDtos = testPage.getContent()
                .stream()
                .map(testPatternMapper::toBaseDto)
                .toList();

        return new PaginationResponse<>(page, size, testDtos);
    }

}

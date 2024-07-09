package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.TestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.TestPatternMapper;
import ru.kosterror.testsforge.coreservice.repository.TestPatternRepository;
import ru.kosterror.testsforge.coreservice.service.TestPatternService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.TestPatternSpecification.*;

@Service
@RequiredArgsConstructor
public class TestPatternServiceImpl implements TestPatternService {

    private final TestPatternRepository testPatternRepository;
    private final TestPatternMapper testPatternMapper;

    @Override
    @Transactional
    public TestPatternDto createFormPattern(UUID userId, UpdateTestPatternDto updateTestPatternDto) {
        var form = testPatternMapper.toEntity(updateTestPatternDto);
        form = testPatternRepository.save(form);

        return testPatternMapper.toDto(form);
    }

    @Override
    public TestPatternDto getFormPattern(UUID id) {
        var form = getFormPatternEntity(id);

        return testPatternMapper.toDto(form);
    }

    @Override
    public TestPatternEntity getFormPatternEntity(UUID id) {
        return testPatternRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form pattern with id %s not found".formatted(id)));
    }

    @Override
    public PaginationResponse<BaseTestPatternDto> getFormPatterns(int page,
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

        var formPage = testPatternRepository.findAll(specification, PageRequest.of(page, size));

        var formDtos = formPage.getContent()
                .stream()
                .map(testPatternMapper::toBaseDto)
                .toList();

        return new PaginationResponse<>(page, size, formDtos);
    }

}

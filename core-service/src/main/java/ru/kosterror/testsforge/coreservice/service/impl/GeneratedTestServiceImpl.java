package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.mapper.GeneratedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.service.GeneratedTestService;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.impl.factory.GeneratedTestFactory;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedTestServiceImpl implements GeneratedTestService {

    private final GeneratedTestRepository generatedTestRepository;
    private final PublishedTestService publishedTestService;
    private final GeneratedTestMapper generatedTestMapper;
    private final GeneratedTestFactory generatedTestFactory;

    @Override
    public GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId) {
        var generatedTest = generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElse(buildAndSaveGeneratedTest(userId, publishedTestId));

        return generatedTestMapper.toDto(generatedTest);
    }

    private GeneratedTestEntity buildAndSaveGeneratedTest(UUID userId, UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        var generatedTest = generatedTestFactory.buildGeneratedTestEntity(publishedTest, userId);

        return generatedTestRepository.save(generatedTest);
    }

}

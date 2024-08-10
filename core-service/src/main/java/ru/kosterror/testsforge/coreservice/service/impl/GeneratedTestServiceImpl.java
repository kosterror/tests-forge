package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.ForbiddenException;
import ru.kosterror.testsforge.coreservice.mapper.GeneratedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.service.GeneratedTestService;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.UserService;
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
    private final UserService userService;

    @Override
    public GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElse(buildAndSaveGeneratedTest(publishedTest, userId));

        return generatedTestMapper.toDto(generatedTest);
    }

    private GeneratedTestEntity buildAndSaveGeneratedTest(PublishedTestEntity publishedTest, UUID userId) {
        var generatedTest = generatedTestFactory.buildGeneratedTestEntity(publishedTest, userId);

        return generatedTestRepository.save(generatedTest);
    }

    private void checkUserAccessForPublishedTest(PublishedTestEntity publishedTest, UUID userId) {
        if (!publishedTest.getUserIds().contains(userId)
                && !userService.isAnyGroupContainsUser(publishedTest.getGroupIds(), userId
        )) {
            throw new ForbiddenException("You don't have access to this published test");
        }

        log.info("User {} has access to published test {}", userId, publishedTest.getId());
    }


}

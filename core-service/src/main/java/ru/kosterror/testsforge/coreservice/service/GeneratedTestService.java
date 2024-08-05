package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;

import java.util.UUID;

public interface GeneratedTestService {

    GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId);

}

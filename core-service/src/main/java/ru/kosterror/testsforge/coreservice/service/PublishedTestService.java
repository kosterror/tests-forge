package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;

import java.util.UUID;

public interface PublishedTestService {

    BasePublishedTestDto publishTest(PublishTestDto publishTestDto);

    PaginationResponse<BasePublishedTestDto> getPublishedTests(String name,
                                                               UUID subjectId,
                                                               UUID groupId,
                                                               int page,
                                                               int size
    );
}

package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;

public interface PublishedTestService {

    BasePublishedTestDto publishTest(PublishTestDto publishTestDto);

}

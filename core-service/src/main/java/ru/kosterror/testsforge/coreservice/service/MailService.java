package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.test.published.PublishedTestAttribute;

import java.util.List;

public interface MailService {

    void sendMailsAboutPublishingTest(String testName, List<String> emails);

    void sendMailsAboutUpdatingPublishedTest(String testName,
                                             List<String> emails,
                                             List<PublishedTestAttribute> updatedAttributes);
}

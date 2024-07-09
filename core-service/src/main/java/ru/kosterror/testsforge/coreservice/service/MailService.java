package ru.kosterror.testsforge.coreservice.service;

import java.util.List;

public interface MailService {

    void sendMailsAboutPublishingTest(String testName, List<String> emails);

}

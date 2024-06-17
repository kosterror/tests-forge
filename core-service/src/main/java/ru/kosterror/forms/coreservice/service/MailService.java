package ru.kosterror.forms.coreservice.service;

import java.util.List;

public interface MailService {

    void sendMailsAboutPublishingForm(String formName, List<String> emails);

}

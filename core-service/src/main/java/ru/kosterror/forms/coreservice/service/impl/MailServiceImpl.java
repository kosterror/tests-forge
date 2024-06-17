package ru.kosterror.forms.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.commonmodel.mail.SendMailDto;
import ru.kosterror.forms.coreservice.producer.SendMailProducer;
import ru.kosterror.forms.coreservice.service.MailService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final SendMailProducer sendMailProducer;

    @Override
    public void sendMailsAboutPublishingForm(String formName, List<String> emails) {
        String subject = "Создана форма \"%s\"".formatted(formName);

        SendMailDto sendMailDto = SendMailDto.builder()
                .subject(subject)
                .body("Добрый день, создана форма \"%s\", не забудьте её заполнить".formatted(formName))
                .receivers(emails)
                .build();

        sendMailProducer.sendMessage(sendMailDto);
    }

}

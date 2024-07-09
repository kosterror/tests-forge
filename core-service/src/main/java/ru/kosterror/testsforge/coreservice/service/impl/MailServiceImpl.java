package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;
import ru.kosterror.testsforge.coreservice.producer.SendMailProducer;
import ru.kosterror.testsforge.coreservice.service.MailService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final SendMailProducer sendMailProducer;

    @Override
    public void sendMailsAboutPublishingTest(String testName, List<String> emails) {
        String subject = "Опубликован тест \"%s\"".formatted(testName);

        SendMailDto sendMailDto = SendMailDto.builder()
                .subject(subject)
                .body("Добрый день, опубликован тест \"%s\", не забудьте его пройти".formatted(testName))
                .receivers(emails)
                .build();

        sendMailProducer.sendMessage(sendMailDto);
    }

}

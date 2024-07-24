package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishedTestAttribute;
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

    @Override
    public void sendMailsAboutUpdatingPublishedTest(String testName,
                                                    List<String> emails,
                                                    List<PublishedTestAttribute> updatedAttributes) {
        var subject = "Изменения в тесте \"%s\"".formatted(testName);

        String body = createBodyForUpdatingPublishedTest(testName, updatedAttributes);

        SendMailDto sendMailDto = SendMailDto.builder()
                .subject(subject)
                .body(body)
                .receivers(emails)
                .build();

        sendMailProducer.sendMessage(sendMailDto);
    }

    private String createBodyForUpdatingPublishedTest(String testName, List<PublishedTestAttribute> updatedAttributes) {
        StringBuilder bodyBuilder = new StringBuilder("Добрый день, в тесте \"%s\" произошли следующие изменения:".formatted(testName));

        for (PublishedTestAttribute attribute : updatedAttributes) {
            bodyBuilder.append("\n - %s".formatted(attribute.getDescription()));
        }

        return bodyBuilder.toString();
    }

}

package ru.kosterror.testsforge.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;
import ru.kosterror.testsforge.userservice.producer.SendMailProducer;
import ru.kosterror.testsforge.userservice.service.MailService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final SendMailProducer sendMailProducer;

    @Override
    public void sendResettingPasswordMail(String email, String password) {
        var sendMailDto = new SendMailDto(
                List.of(email),
                Collections.emptyList(),
                Collections.emptyList(),
                "Ваш пароль был сброшен",
                "Ваш новый пароль: %s".formatted(password)
        );

        sendMailProducer.sendMessage(sendMailDto);
    }
}

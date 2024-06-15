package ru.kosterror.forms.mailservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.commonmodel.mail.SendMailDto;
import ru.kosterror.forms.mailservice.service.MailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailConsumer {

    private static final String TOPIC = "${application.kafka.topic.send-mail.name}";

    private final MailService mailService;

    @KafkaListener(topics = TOPIC)
    public void consumeSendMailMessage(SendMailDto sendMailDto) {
        log.info("Consumed message {}", sendMailDto);
        mailService.sendEmail(sendMailDto);
    }

}

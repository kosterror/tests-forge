package ru.kosterror.forms.mailservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.commonmodel.mail.SendMailDto;

@Slf4j
@Component
public class SendMailConsumer {

    private static final String TOPIC = "${application.kafka.topic.send-mail.name}";

    @KafkaListener(topics = TOPIC)
    public void consumeSendMailMessage(SendMailDto sendMailDto) {
        log.info("Consumed message {}", sendMailDto);
    }

}

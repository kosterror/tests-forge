package ru.kosterror.testsforge.userservice.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailProducer {

    private final KafkaTemplate<String, SendMailDto> kafkaTemplate;

    @Value("${application.kafka.topic.send-mail}")
    private String topic;

    public void sendMessage(SendMailDto sendMailDto) {
        log.info("Producing message: {}", sendMailDto);
        kafkaTemplate.send(topic, sendMailDto);
    }

}

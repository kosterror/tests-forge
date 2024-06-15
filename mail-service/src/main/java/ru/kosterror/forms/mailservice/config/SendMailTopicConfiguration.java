package ru.kosterror.forms.mailservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application.kafka.topic.send-mail")
public class SendMailTopicConfiguration {

    private final String name;
    private final int partitions;
    private final short replicationFactor;

    @Bean
    public NewTopic sendMailTopic() {
        return TopicBuilder.name(name)
                .partitions(partitions)
                .replicas(replicationFactor)
                .build();
    }

}

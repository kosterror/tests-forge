package ru.kosterror.forms.mailservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@Builder
public class MailDetailsEntity {

    @Id
    private String id;

    private List<String> receivers;

    private List<String> copies;

    private List<String> hiddenCopies;

    private String subject;

    private String body;

    private LocalDateTime sendDate;

    private LocalDateTime createdDate;

    private MailStatus status;

}

package ru.kosterror.testsforge.mailservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mail_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "mail_receiver")
    @ElementCollection
    @CollectionTable(name = "mail_receiver", joinColumns = @JoinColumn(name = "mail_details_id"))
    private List<String> receivers;

    @Column(name = "mail_copy")
    @ElementCollection
    @CollectionTable(name = "mail_copy", joinColumns = @JoinColumn(name = "mail_details_id"))
    private List<String> copies;

    @Column(name = "mail_hidden_copy")
    @ElementCollection
    @CollectionTable(name = "mail_hidden_copy", joinColumns = @JoinColumn(name = "mail_details_id"))
    private List<String> hiddenCopies;

    private String subject;

    private String body;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private MailStatus status;

}

package ru.kosterror.forms.coreservice.entity.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class QuestionEntity extends BaseEntity {

    private String title;

    private UUID ownerId;

    @Enumerated(value = EnumType.STRING)
    private QuestionType type;

    private int points;

    private Boolean isVisible;

    @ElementCollection
    @CollectionTable(name = "question_attachment", joinColumns = @JoinColumn(name = "question_id"))
    private List<UUID> attachments;

}

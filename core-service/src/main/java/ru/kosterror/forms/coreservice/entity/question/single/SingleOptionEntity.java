package ru.kosterror.forms.coreservice.entity.question.single;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Table(name = "single_option")
@Getter
@Setter
public class SingleOptionEntity extends BaseEntity {

    private String value;

    private int sequenceNumber;

    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SingleChoiceQuestionEntity question;
}

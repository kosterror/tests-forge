package ru.kosterror.forms.entity.question.multiple;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.entity.BaseEntity;

@Entity
@Table(name = "multiple_option")
@Getter
@Setter
public class MultipleOptionEntity extends BaseEntity {

    private String value;

    private int sequenceNumber;

    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private MultipleChoiceQuestionEntity question;
}

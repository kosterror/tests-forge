package ru.kosterror.forms.coreservice.entity.question.multiple;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Table(name = "multiple_option")
@Getter
@Setter
public class MultipleOptionEntity extends BaseEntity {

    private String name;

    @Column(name = "\"order\"")
    private Integer order;

    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private MultipleChoiceQuestionEntity question;
}

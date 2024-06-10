package ru.kosterror.forms.coreservice.entity.question.single;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Table(name = "single_option")
@Getter
@Setter
public class SingleOptionEntity extends BaseEntity {

    private String name;

    @Column(name = "\"order\"")
    private Integer order;

    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SingleChoiceQuestionEntity question;
}

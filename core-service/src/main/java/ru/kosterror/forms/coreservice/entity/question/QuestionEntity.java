package ru.kosterror.forms.coreservice.entity.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class QuestionEntity extends BaseEntity {

    private String question;

    private String comment;

    @Enumerated(value = EnumType.STRING)
    private QuestionType type;

    private int points;

}

package ru.kosterror.forms.entity.question;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.entity.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class QuestionEntity extends BaseEntity {

    private String question;

    private String comment;

    private QuestionType type;

    private int points;

}

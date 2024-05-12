package ru.kosterror.forms.coreservice.entity.question.matching;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Table(name = "term")
@Getter
@Setter
public class TermEntity extends BaseEntity {

    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private MatchingQuestionEntity question;

    @OneToOne
    private DefinitionEntity definition;

}

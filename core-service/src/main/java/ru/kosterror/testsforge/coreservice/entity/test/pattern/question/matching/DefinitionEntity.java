package ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;

@Entity
@Table(name = "definition")
@Getter
@Setter
public class DefinitionEntity extends BaseEntity {

    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private MatchingQuestionEntity question;

}
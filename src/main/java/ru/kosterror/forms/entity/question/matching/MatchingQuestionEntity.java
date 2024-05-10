package ru.kosterror.forms.entity.question.matching;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "matching_question")
@Getter
@Setter
public class MatchingQuestionEntity extends QuestionEntity {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TermEntity> terms;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<DefinitionEntity> definitions;

}

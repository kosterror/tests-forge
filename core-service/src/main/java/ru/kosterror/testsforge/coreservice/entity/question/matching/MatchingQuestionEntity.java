package ru.kosterror.testsforge.coreservice.entity.question.matching;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "matching_question")
@Getter
@Setter
public class MatchingQuestionEntity extends QuestionEntity {

    @ElementCollection
    @CollectionTable(name = "matching_question_points", joinColumns = @JoinColumn(name = "question_id"))
    private Map<Integer, Integer> points;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TermEntity> terms;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<DefinitionEntity> definitions;

}

package ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "multiple_choice_question")
@Getter
@Setter
public class MultipleChoiceQuestionEntity extends QuestionEntity {

    @ElementCollection
    @CollectionTable(name = "multiple_question_points", joinColumns = @JoinColumn(name = "question_id"))
    private Map<Integer, Integer> points;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("order")
    private List<MultipleOptionEntity> options;

}


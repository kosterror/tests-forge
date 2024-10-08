package ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "single_choice_question")
@Getter
@Setter
public class SingleChoiceQuestionEntity extends QuestionEntity {

    private Integer points;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("order")
    private List<SingleOptionEntity> options;

}

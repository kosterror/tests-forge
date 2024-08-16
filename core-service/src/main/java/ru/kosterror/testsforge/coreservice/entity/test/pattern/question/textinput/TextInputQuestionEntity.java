package ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "text_input_question")
@Getter
@Setter
public class TextInputQuestionEntity extends QuestionEntity {

    private Integer points;

    private Boolean isCaseSensitive;

    @Column(name = "answer")
    @CollectionTable(name = "text_input_answer_question", joinColumns = @JoinColumn(name = "question_id"))
    @ElementCollection
    private List<String> answers;

}

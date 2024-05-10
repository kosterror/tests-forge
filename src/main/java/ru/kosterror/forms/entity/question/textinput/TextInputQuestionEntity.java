package ru.kosterror.forms.entity.question.textinput;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "text_input_question")
@Getter
@Setter
public class TextInputQuestionEntity extends QuestionEntity {

    private Boolean isCaseSensitive;

    @CollectionTable(name = "text_input_answer_question")
    @ElementCollection
    private List<String> answers;

}

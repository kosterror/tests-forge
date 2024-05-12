package ru.kosterror.forms.coreservice.entity.question.multiple;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "multiple_choice_question")
@Getter
@Setter
public class MultipleChoiceQuestionEntity extends QuestionEntity {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("sequenceNumber")
    private List<MultipleOptionEntity> options;

}


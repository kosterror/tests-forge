package ru.kosterror.forms.entity.question.single;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "single_choice_question")
@Getter
@Setter
public class SingleChoiceQuestionEntity extends QuestionEntity {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("sequenceNumber")
    private List<SingleOptionEntity> options;

}

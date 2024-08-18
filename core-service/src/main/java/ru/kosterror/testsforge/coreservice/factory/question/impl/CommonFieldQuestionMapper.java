package ru.kosterror.testsforge.coreservice.factory.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.NewQuestionType;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.InternalException;

@Component
public class CommonFieldQuestionMapper {

    public void mapCommonFields(NewQuestionDto source, QuestionEntity target) {
        target.setName(source.getName());
        target.setAttachments(source.getAttachments());
        target.setType(mapType(source.getType()));
    }

    private QuestionType mapType(NewQuestionType type) {
        return switch (type) {
            case SINGLE_CHOICE -> QuestionType.SINGLE_CHOICE;
            case MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE;
            case MATCHING -> QuestionType.MATCHING;
            case TEXT_INPUT -> QuestionType.TEXT_INPUT;
            case BASED_ON_EXISTING -> throw new InternalException("Cannot map 'BASED_ON_EXISTING' to QuestionType");
        };
    }

}

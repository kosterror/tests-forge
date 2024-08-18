package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.MatchingOptionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.MatchingQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.TermDefinitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

import java.util.ArrayList;

@Component
public class MatchingQuestionMapper extends BaseQuestionMapper {

    public MatchingQuestionMapper(SubjectMapper subjectMapper) {
        super(subjectMapper);
    }

    @Override
    public QuestionDto toDto(QuestionEntity baseEntity) {
        var dto = new MatchingQuestionDto();
        mapBaseQuestionDtoFields(dto, baseEntity);

        var entity = (MatchingQuestionEntity) baseEntity;

        dto.setPoints(entity.getPoints());

        var termsAndDefinitions = new ArrayList<TermDefinitionDto>();

        for (var term : entity.getTerms()) {
            var termDto = new MatchingOptionDto(term.getId(), term.getText());
            var definitionDto = new MatchingOptionDto(term.getDefinition().getId(), term.getDefinition().getText());
            var termDefinitionDto = new TermDefinitionDto(termDto, definitionDto);

            termsAndDefinitions.add(termDefinitionDto);
        }

        dto.setTermAndDefinitions(termsAndDefinitions);

        return dto;
    }


}

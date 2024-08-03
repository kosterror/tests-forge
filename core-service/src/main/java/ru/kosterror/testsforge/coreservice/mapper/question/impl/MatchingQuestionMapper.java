package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateMatchingQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.MatchingOptionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.MatchingQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.matching.TermDefinitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.DefinitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.TermEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

import java.util.ArrayList;
import java.util.Map;

@Component
public class MatchingQuestionMapper extends BaseQuestionMapper {

    public MatchingQuestionMapper(SubjectMapper subjectMapper) {
        super(subjectMapper);
    }

    private static void mapTermAndDefinition(Map.Entry<String, String> termAndDefinition,
                                             ArrayList<DefinitionEntity> definitions,
                                             ArrayList<TermEntity> terms) {
        var definition = new DefinitionEntity();
        definition.setText(termAndDefinition.getValue());
        definitions.add(definition);

        var term = new TermEntity();
        term.setText(termAndDefinition.getKey());
        term.setDefinition(definition);
        terms.add(term);
    }

    @Override
    public QuestionEntity toEntity(CreateQuestionDto baseDto) {
        var entity = new MatchingQuestionEntity();
        mapBaseQuestionEntityFields(entity, baseDto);

        var dto = (CreateMatchingQuestionDto) baseDto;

        int listsSize = dto.getTermsAndDefinitions().size();
        var terms = new ArrayList<TermEntity>(listsSize);
        var definitions = new ArrayList<DefinitionEntity>(listsSize);

        for (var termAndDefinition : dto.getTermsAndDefinitions().entrySet()) {
            mapTermAndDefinition(termAndDefinition, definitions, terms);
        }

        entity.setTerms(terms);
        entity.setDefinitions(definitions);
        entity.setPoints(dto.getPoints());

        return entity;
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

package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewMatchingQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.DefinitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.TermEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MatchingQuestionFactory {

    private final CommonFieldQuestionMapper commonFieldQuestionMapper;

    public MatchingQuestionEntity buildFromDto(NewMatchingQuestionDto questionDto) {
        var questionEntity = new MatchingQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionDto, questionEntity);

        int listsSize = questionDto.getTermsAndDefinitions().size();
        var terms = new ArrayList<TermEntity>(listsSize);
        var definitions = new ArrayList<DefinitionEntity>(listsSize);

        for (var termAndDefinition : questionDto.getTermsAndDefinitions().entrySet()) {
            buildTermAndDefinitionEntities(termAndDefinition, definitions, terms, questionEntity);
        }

        questionEntity.setTerms(terms);
        questionEntity.setDefinitions(definitions);
        questionEntity.setPoints(questionDto.getPoints());

        return questionEntity;
    }

    public MatchingQuestionEntity buildFromEntity(MatchingQuestionEntity questionEntity) {
        var dto = new NewMatchingQuestionDto();
        var termAndDefinitions = buildTermDefinitionsMap(questionEntity);

        dto.setName(questionEntity.getName());
        dto.setAttachments(new ArrayList<>(questionEntity.getAttachments()));
        dto.setPoints(new HashMap<>(questionEntity.getPoints()));
        dto.setTermsAndDefinitions(termAndDefinitions);

        return buildFromDto(dto);
    }

    private void buildTermAndDefinitionEntities(Map.Entry<String, String> termAndDefinition,
                                                ArrayList<DefinitionEntity> definitions,
                                                ArrayList<TermEntity> terms,
                                                MatchingQuestionEntity question) {
        var definition = new DefinitionEntity();
        definition.setText(termAndDefinition.getValue());
        definition.setQuestion(question);
        definitions.add(definition);

        var term = new TermEntity();
        term.setText(termAndDefinition.getKey());
        term.setDefinition(definition);
        term.setQuestion(question);
        terms.add(term);
    }

    private HashMap<String, String> buildTermDefinitionsMap(MatchingQuestionEntity questionEntity) {
        var terms = questionEntity.getTerms();
        var termAndDefinitions = new HashMap<String, String>();

        for (var term : terms) {
            termAndDefinitions.put(
                    term.getText(),
                    term.getDefinition().getText()
            );
        }
        return termAndDefinitions;
    }

}

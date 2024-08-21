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
        var newQuestionEntity = new MatchingQuestionEntity();

        var terms = new ArrayList<TermEntity>(questionEntity.getTerms().size());
        var definitions = new ArrayList<DefinitionEntity>(questionEntity.getDefinitions().size());

        for (var term : questionEntity.getTerms()) {
            var newDefinition = buildDefinitionFromEntity(term, newQuestionEntity);
            var newTerm = buildDefinitionFromEntity(term, newQuestionEntity, newDefinition);

            terms.add(newTerm);
            definitions.add(newDefinition);
        }


        newQuestionEntity.setPoints(new HashMap<>(questionEntity.getPoints()));
        newQuestionEntity.setTerms(terms);
        newQuestionEntity.setDefinitions(definitions);

        commonFieldQuestionMapper.mapCommonFields(questionEntity, newQuestionEntity);

        return newQuestionEntity;
    }

    private TermEntity buildDefinitionFromEntity(TermEntity term,
                                                 MatchingQuestionEntity newQuestionEntity,
                                                 DefinitionEntity newDefinition
    ) {
        var newTerm = new TermEntity();
        newTerm.setText(term.getText());
        newTerm.setQuestion(newQuestionEntity);
        newTerm.setDefinition(newDefinition);
        return newTerm;
    }

    private DefinitionEntity buildDefinitionFromEntity(TermEntity term,
                                                       MatchingQuestionEntity newQuestionEntity
    ) {
        var definition = term.getDefinition();
        var newDefinition = new DefinitionEntity();
        newDefinition.setText(definition.getText());
        newDefinition.setQuestion(newQuestionEntity);
        return newDefinition;
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

}

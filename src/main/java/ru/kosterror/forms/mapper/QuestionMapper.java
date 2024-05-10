package ru.kosterror.forms.mapper;

import org.springframework.stereotype.Component;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.full.matching.MatchingOptionDto;
import ru.kosterror.forms.dto.question.full.matching.MatchingQuestionDto;
import ru.kosterror.forms.dto.question.full.matching.TermDefinitionDto;
import ru.kosterror.forms.dto.question.full.multiple.MultipleChoiceQuestionDto;
import ru.kosterror.forms.dto.question.full.multiple.MultipleOptionDto;
import ru.kosterror.forms.dto.question.full.single.SingleChoiceQuestionDto;
import ru.kosterror.forms.dto.question.full.single.SingleOptionDto;
import ru.kosterror.forms.dto.question.full.textinput.TextInputQuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.*;
import ru.kosterror.forms.entity.question.QuestionEntity;
import ru.kosterror.forms.entity.question.matching.DefinitionEntity;
import ru.kosterror.forms.entity.question.matching.MatchingQuestionEntity;
import ru.kosterror.forms.entity.question.matching.TermEntity;
import ru.kosterror.forms.entity.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.forms.entity.question.multiple.MultipleOptionEntity;
import ru.kosterror.forms.entity.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.forms.entity.question.single.SingleOptionEntity;
import ru.kosterror.forms.entity.question.textinput.TextInputQuestionEntity;

import java.util.ArrayList;

@Component
public class QuestionMapper {

    public QuestionEntity toEntity(NewQuestionDto dto) {
        return switch (dto.getType()) {
            case SINGLE_CHOICE -> buildSingleChoiceQuestionEntity((NewSingleChoiceQuestionDto) dto);
            case MULTIPLE_CHOICE -> buildMultipleChoiceQuestionEntity((NewMultipleChoiceQuestionDto) dto);
            case TEXT_INPUT -> buildTextInputQuestionEntity((NewTextInputQuestionDto) dto);
            case MATCHING -> buildMatchingQuestionEntity((NewMatchingQuestionDto) dto);
        };
    }

    public QuestionDto toDto(QuestionEntity entity) {
        return switch (entity.getType()) {
            case SINGLE_CHOICE -> buildSingleChoiceQuestionDto((SingleChoiceQuestionEntity) entity);
            case MULTIPLE_CHOICE -> buildMultipleChoiceQuestionDto((MultipleChoiceQuestionEntity) entity);
            case TEXT_INPUT -> buildTextInputQuestionDto((TextInputQuestionEntity) entity);
            case MATCHING -> buildMatchingQuestionDto((MatchingQuestionEntity) entity);
        };
    }

    private SingleChoiceQuestionDto buildSingleChoiceQuestionDto(SingleChoiceQuestionEntity entity) {
        var dto = new SingleChoiceQuestionDto();
        mapBaseQuestionDtoFields(dto, entity);

        var options = entity.getOptions()
                .stream()
                .map(option -> {
                    var optionDto = new SingleOptionDto();
                    optionDto.setId(option.getId());
                    optionDto.setValue(option.getValue());
                    optionDto.setRight(option.getIsRight());
                    return optionDto;
                })
                .toList();

        dto.setOptions(options);

        return dto;
    }

    private MultipleChoiceQuestionDto buildMultipleChoiceQuestionDto(MultipleChoiceQuestionEntity entity) {
        var dto = new MultipleChoiceQuestionDto();
        mapBaseQuestionDtoFields(dto, entity);

        var options = entity.getOptions()
                .stream()
                .map(option -> {
                    var optionDto = new MultipleOptionDto();
                    optionDto.setId(option.getId());
                    optionDto.setValue(option.getValue());
                    optionDto.setRight(option.getIsRight());
                    return optionDto;
                })
                .toList();

        dto.setOptions(options);

        return dto;
    }

    private TextInputQuestionDto buildTextInputQuestionDto(TextInputQuestionEntity entity) {
        var dto = new TextInputQuestionDto();
        mapBaseQuestionDtoFields(dto, entity);

        dto.setCaseSensitive(entity.getIsCaseSensitive());
        dto.setAnswers(entity.getAnswers());

        return dto;
    }

    private MatchingQuestionDto buildMatchingQuestionDto(MatchingQuestionEntity entity) {
        var dto = new MatchingQuestionDto();
        mapBaseQuestionDtoFields(dto, entity);

        var terms = entity.getTerms();
        var termDefinitionDtos = new ArrayList<TermDefinitionDto>(terms.size());

        for (TermEntity term : terms) {
            var termDto = new MatchingOptionDto(term.getId(), term.getText());
            var definitionDto = new MatchingOptionDto(term.getDefinition().getId(), term.getDefinition().getText());
            var termDefinitionDto = new TermDefinitionDto(termDto, definitionDto);

            termDefinitionDtos.add(termDefinitionDto);
        }

        dto.setTermAndDefinitions(termDefinitionDtos);

        return dto;
    }

    private void mapBaseQuestionDtoFields(QuestionDto dto, QuestionEntity entity) {
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setComment(entity.getComment());
        dto.setPoints(entity.getPoints());
        dto.setType(entity.getType());
    }

    private SingleChoiceQuestionEntity buildSingleChoiceQuestionEntity(NewSingleChoiceQuestionDto dto) {
        var entity = new SingleChoiceQuestionEntity();
        mapBaseQuestionEntityFields(entity, dto);

        var optionNames = dto.getOptions();
        var optionEntities = new ArrayList<SingleOptionEntity>(optionNames.size());

        for (int i = 0; i < optionNames.size(); i++) {
            var optionEntity = new SingleOptionEntity();
            optionEntity.setValue(optionNames.get(i));
            optionEntity.setSequenceNumber(i);
            optionEntity.setIsRight(dto.getCorrectOptionIndex() == i);
            optionEntity.setQuestion(entity);

            optionEntities.add(optionEntity);
        }

        entity.setOptions(optionEntities);
        return entity;
    }

    private MultipleChoiceQuestionEntity buildMultipleChoiceQuestionEntity(NewMultipleChoiceQuestionDto dto) {
        var entity = new MultipleChoiceQuestionEntity();
        mapBaseQuestionEntityFields(entity, dto);

        var optionNames = dto.getOptions();
        var optionEntities = new ArrayList<MultipleOptionEntity>(optionNames.size());

        for (int i = 0; i < optionNames.size(); i++) {
            var optionEntity = new MultipleOptionEntity();
            optionEntity.setValue(optionNames.get(i));
            optionEntity.setSequenceNumber(i);
            optionEntity.setIsRight(dto.getCorrectOptionIndices().contains(i));
            optionEntity.setQuestion(entity);

            optionEntities.add(optionEntity);
        }

        entity.setOptions(optionEntities);
        return entity;
    }

    private TextInputQuestionEntity buildTextInputQuestionEntity(NewTextInputQuestionDto dto) {
        var entity = new TextInputQuestionEntity();
        mapBaseQuestionEntityFields(entity, dto);

        entity.setIsCaseSensitive(dto.isCaseSensitive());
        entity.setAnswers(dto.getAnswers());

        return entity;
    }

    private QuestionEntity buildMatchingQuestionEntity(NewMatchingQuestionDto dto) {
        var entity = new MatchingQuestionEntity();
        mapBaseQuestionEntityFields(entity, dto);

        int listsSize = dto.getTermsAndDefinitions().size();
        var terms = new ArrayList<TermEntity>(listsSize);
        var definitions = new ArrayList<DefinitionEntity>(listsSize);

        for (var termAndDefinition : dto.getTermsAndDefinitions().entrySet()) {
            var definition = new DefinitionEntity();
            definition.setText(termAndDefinition.getValue());
            definition.setQuestion(entity);

            var term = new TermEntity();
            term.setText(termAndDefinition.getKey());
            term.setDefinition(definition);
            term.setQuestion(entity);

            terms.add(term);
            definitions.add(definition);
        }

        entity.setTerms(terms);
        entity.setDefinitions(definitions);

        return entity;
    }

    private <T extends QuestionEntity> void mapBaseQuestionEntityFields(T entity, NewQuestionDto dto) {
        entity.setQuestion(dto.getQuestion());
        entity.setComment(dto.getComment());
        entity.setPoints(dto.getPoints());
        entity.setType(dto.getType());
    }

}

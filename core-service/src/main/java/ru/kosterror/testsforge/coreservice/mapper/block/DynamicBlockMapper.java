package ru.kosterror.testsforge.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.BlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.DynamicBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateDynamicBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.factory.question.QuestionFactory;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;

@Component
@RequiredArgsConstructor
public class DynamicBlockMapper extends BaseBlockMapper {

    private final QuestionMapper questionMapper;
    private final QuestionFactory questionFactory;

    @Override
    public BlockEntity toEntity(UpdateBlockDto baseDto) {
        var entity = new DynamicBlockEntity();
        mapBaseBlockEntityFields(entity, baseDto);

        var dto = (UpdateDynamicBlockDto) baseDto;

        entity.setQuestionCount(dto.getQuestionCount());

        mapQuestions(entity, dto);

        return entity;
    }

    private void mapQuestions(DynamicBlockEntity entity, UpdateDynamicBlockDto dto) {
        var questions = dto.getQuestions().stream().map(questionFactory::buildQuestion).toList();

        for (var question : questions) {
            question.setDynamicBlock(entity);
        }

        entity.setQuestions(questions);
    }

    @Override
    public BlockDto toDto(BlockEntity baseEntity) {
        var dto = new DynamicBlockDto();
        mapBaseBlockDtoFields(dto, baseEntity);

        var entity = (DynamicBlockEntity) baseEntity;

        dto.setQuestions(questionMapper.toDtos(entity.getQuestions()));
        dto.setQuestionCount(entity.getQuestionCount());

        return dto;
    }
}

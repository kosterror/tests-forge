package ru.kosterror.testsforge.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.DynamicBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;

@Component
@RequiredArgsConstructor
public class DynamicBlockMapper extends BaseBlockMapper {

    private final QuestionMapper questionMapper;

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

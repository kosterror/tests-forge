package ru.kosterror.forms.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.DynamicBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateDynamicBlockDto;
import ru.kosterror.forms.coreservice.entity.form.BlockEntity;
import ru.kosterror.forms.coreservice.entity.form.DynamicBlockEntity;
import ru.kosterror.forms.coreservice.mapper.question.QuestionMapper;

@Component
@RequiredArgsConstructor
public class DynamicBlockMapper extends BaseBlockMapper {

    private final QuestionMapper questionMapper;

    @Override
    public BlockEntity toEntity(UpdateBlockDto baseDto) {
        var entity = new DynamicBlockEntity();
        mapBaseBlockEntityFields(entity, baseDto);

        var dto = (UpdateDynamicBlockDto) baseDto;

        mapQuestions(entity, dto);

        return entity;
    }

    private void mapQuestions(DynamicBlockEntity entity, UpdateDynamicBlockDto dto) {
        var questions = questionMapper.toEntities(dto.getQuestions());

        if (questions != null) {
            for (var question : questions) {
                question.setDynamicBlock(entity);
            }
        }

        entity.setQuestions(questions);
    }

    @Override
    public BlockDto toDto(BlockEntity baseEntity) {
        var dto = new DynamicBlockDto();
        mapBaseBlockDtoFields(dto, baseEntity);

        var entity = (DynamicBlockEntity) baseEntity;

        dto.setQuestions(questionMapper.toDtos(entity.getQuestions()));

        return dto;
    }
}

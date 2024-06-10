package ru.kosterror.forms.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.coreservice.dto.formpattern.createupdate.CreateBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BlockDto;
import ru.kosterror.forms.coreservice.entity.form.BlockEntity;

@Component
@RequiredArgsConstructor
public class BlockMapper {

    private final DynamicBlockMapper dynamicBlockMapper;
    private final StaticBlockMapper staticBlockMapper;

    public BlockEntity toEntity(CreateBlockDto dto) {
        return switch (dto.getType()) {
            case DYNAMIC -> dynamicBlockMapper.toEntity(dto);
            case STATIC -> staticBlockMapper.toEntity(dto);
        };
    }

    public BlockDto toDto(BlockEntity entity) {
        return switch (entity.getType()) {
            case DYNAMIC -> dynamicBlockMapper.toDto(entity);
            case STATIC -> staticBlockMapper.toDto(entity);
        };
    }

}

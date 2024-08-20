package ru.kosterror.testsforge.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.StaticBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.mapper.VariantMapper;

@Component
@RequiredArgsConstructor
public class StaticBlockMapper extends BaseBlockMapper {

    private final VariantMapper variantMapper;

    @Override
    public BlockDto toDto(BlockEntity baseEntity) {
        var dto = new StaticBlockDto();
        mapBaseBlockDtoFields(dto, baseEntity);

        var entity = (StaticBlockEntity) baseEntity;
        dto.setVariants(variantMapper.toDtos(entity.getVariants()));

        return dto;
    }

}

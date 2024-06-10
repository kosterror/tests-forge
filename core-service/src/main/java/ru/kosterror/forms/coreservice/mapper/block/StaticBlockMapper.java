package ru.kosterror.forms.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.StaticBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateStaticBlockDto;
import ru.kosterror.forms.coreservice.entity.form.BlockEntity;
import ru.kosterror.forms.coreservice.entity.form.StaticBlockEntity;
import ru.kosterror.forms.coreservice.mapper.VariantMapper;

@Component
@RequiredArgsConstructor
public class StaticBlockMapper extends BaseBlockMapper {

    private final VariantMapper variantMapper;

    @Override
    public BlockEntity toEntity(UpdateBlockDto baseDto) {
        var entity = new StaticBlockEntity();
        mapBaseBlockEntityFields(entity, baseDto);

        var dto = (UpdateStaticBlockDto) baseDto;

        var variants = variantMapper.toEntities(dto.getVariants());
        entity.setVariants(variants);

        setBlockToVariants(entity);

        return entity;
    }

    @Override
    public BlockDto toDto(BlockEntity baseEntity) {
        var dto = new StaticBlockDto();
        mapBaseBlockDtoFields(dto, baseEntity);

        var entity = (StaticBlockEntity) baseEntity;
        dto.setVariants(variantMapper.toDtos(entity.getVariants()));

        return dto;
    }

    private void setBlockToVariants(StaticBlockEntity entity) {
        if (entity.getVariants() != null) {
            for (var variant : entity.getVariants()) {
                variant.setBlock(entity);
            }
        }
    }

}

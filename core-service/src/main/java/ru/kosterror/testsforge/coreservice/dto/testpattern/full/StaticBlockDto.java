package ru.kosterror.testsforge.coreservice.dto.testpattern.full;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StaticBlockDto extends BlockDto {

    private List<VariantDto> variants;

}

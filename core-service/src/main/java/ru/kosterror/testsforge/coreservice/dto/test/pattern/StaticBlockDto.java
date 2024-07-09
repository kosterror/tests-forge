package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StaticBlockDto extends BlockDto {

    private List<VariantDto> variants;

}

package ru.kosterror.forms.coreservice.dto.form.full;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StaticBlockDto extends BlockDto {

    private List<VariantDto> variants;

}

package ru.kosterror.forms.commonmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class PaginationResponse<T> {

    @Schema(description = "Номер страницы", requiredMode = REQUIRED)
    private int pageNumber;

    @Schema(description = "Размер страницы", requiredMode = REQUIRED)
    private int pageSize;

    @Schema(description = "Элементы", requiredMode = REQUIRED)
    private List<T> elements;

}
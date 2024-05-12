package ru.kosterror.forms.commondto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class PaginationResponse<T> {

    @Schema(description = "Number of page", requiredMode = REQUIRED)
    private int pageNumber;

    @Schema(description = "Size of page", requiredMode = REQUIRED)
    private int pageSize;

    @Schema(description = "Elements", requiredMode = REQUIRED)
    private List<T> elements;

}
package ru.kosterror.testsforge.coreservice.dto.test.generated.verification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class VerificationQuestion {

    @Schema(description = "Вопрос из банка с правильным ответом", requiredMode = REQUIRED)
    private QuestionDto questionWithCorrectAnswer;

    @Schema(description = "Вопрос студента с его ответом", requiredMode = REQUIRED)
    private Question questionWithStudentAnswer;

}

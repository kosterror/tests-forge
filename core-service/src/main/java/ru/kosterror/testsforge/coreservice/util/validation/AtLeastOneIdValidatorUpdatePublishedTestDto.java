package ru.kosterror.testsforge.coreservice.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kosterror.testsforge.coreservice.dto.test.published.UpdatePublishedTestDto;

import static org.springframework.util.CollectionUtils.isEmpty;


public class AtLeastOneIdValidatorUpdatePublishedTestDto
        implements ConstraintValidator<AtLeastOneId, UpdatePublishedTestDto> {

    @Override
    public boolean isValid(UpdatePublishedTestDto value, ConstraintValidatorContext context) {
        var groupsIds = value.groupIds();
        var usersIds = value.userIds();

        return !isEmpty(groupsIds) || !isEmpty(usersIds);
    }

}

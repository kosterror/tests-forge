package ru.kosterror.testsforge.coreservice.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;

import static org.springframework.util.CollectionUtils.isEmpty;

public class AtLeastOneIdValidatorPublishTestDto implements ConstraintValidator<AtLeastOneId, PublishTestDto> {

    @Override
    public boolean isValid(PublishTestDto value, ConstraintValidatorContext context) {
        var groupsIds = value.getGroupIds();
        var usersIds = value.getUserIds();

        return !isEmpty(groupsIds) || !isEmpty(usersIds);
    }

}

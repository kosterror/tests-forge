package ru.kosterror.forms.coreservice.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kosterror.forms.coreservice.dto.PublishFormDto;

import static org.springframework.util.CollectionUtils.isEmpty;

public class AtLeastOneIdValidator implements ConstraintValidator<AtLeastOneId, PublishFormDto> {

    @Override
    public boolean isValid(PublishFormDto value, ConstraintValidatorContext context) {
        var groupsIds = value.getGroupIds();
        var usersIds = value.getUserIds();

        return !isEmpty(groupsIds) || !isEmpty(usersIds);
    }

}

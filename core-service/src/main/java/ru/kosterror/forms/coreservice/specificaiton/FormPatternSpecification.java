package ru.kosterror.forms.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.forms.coreservice.entity.BaseEntity_;
import ru.kosterror.forms.coreservice.entity.form.FormPatternEntity;
import ru.kosterror.forms.coreservice.entity.form.FormPatternEntity_;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormPatternSpecification {

    public static Specification<FormPatternEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                root.get(FormPatternEntity_.NAME),
                like(name)
        );

    }

    public static Specification<FormPatternEntity> hasOwner(UUID ownerId) {
        if (ownerId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.get(FormPatternEntity_.OWNER_ID),
                ownerId
        );
    }

    public static Specification<FormPatternEntity> hasSubjectId(UUID subjectId) {
        if (subjectId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(FormPatternEntity_.SUBJECT)
                        .get(BaseEntity_.ID),
                subjectId

        );
    }


    public static Specification<FormPatternEntity> orderByName() {
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(root.get(FormPatternEntity_.NAME))
            );

            return null;
        };
    }


}

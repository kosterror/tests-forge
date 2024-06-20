package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity_;
import ru.kosterror.testsforge.coreservice.entity.form.FormPatternEntity;
import ru.kosterror.testsforge.coreservice.entity.form.FormPatternEntity_;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormPatternSpecification {

    public static Specification<FormPatternEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                cb.lower(root.get(FormPatternEntity_.NAME)),
                like(name.toLowerCase())
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

    public static Specification<FormPatternEntity> hasSubject(UUID subjectId) {
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

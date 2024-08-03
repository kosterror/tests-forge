package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity_;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestPatternSpecification {

    public static Specification<TestPatternEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                cb.lower(root.get(TestPatternEntity_.NAME)),
                like(name.toLowerCase())
        );

    }

    public static Specification<TestPatternEntity> hasOwner(UUID ownerId) {
        if (ownerId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.get(TestPatternEntity_.OWNER_ID),
                ownerId
        );
    }

    public static Specification<TestPatternEntity> hasSubject(UUID subjectId) {
        if (subjectId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(TestPatternEntity_.SUBJECT)
                        .get(BaseEntity_.ID),
                subjectId

        );
    }


    public static Specification<TestPatternEntity> orderByName() {
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(root.get(TestPatternEntity_.NAME))
            );

            return null;
        };
    }


}

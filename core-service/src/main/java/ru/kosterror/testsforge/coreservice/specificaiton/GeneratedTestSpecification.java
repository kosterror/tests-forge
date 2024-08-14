package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity_;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratedTestSpecification {

    public static Specification<GeneratedTestEntity> hasUserId(UUID userId) {
        if (userId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.get(GeneratedTestEntity_.USER_ID),
                userId
        );
    }

    public static Specification<GeneratedTestEntity> hasSubjectId(UUID subjectId) {
        if (subjectId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(GeneratedTestEntity_.PUBLISHED_TEST)
                        .join(PublishedTestEntity_.TEST_PATTERN)
                        .join(TestPatternEntity_.SUBJECT)
                        .get(BaseEntity_.ID),
                subjectId
        );
    }

    public static Specification<GeneratedTestEntity> hasPublishedTestId(UUID publishedTestId) {
        if (publishedTestId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(GeneratedTestEntity_.PUBLISHED_TEST)
                        .get(BaseEntity_.ID),
                publishedTestId
        );
    }

    public static Specification<GeneratedTestEntity> hasStatus(GeneratedTestStatus status) {
        if (status == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.get(GeneratedTestEntity_.STATUS),
                status
        );
    }

}

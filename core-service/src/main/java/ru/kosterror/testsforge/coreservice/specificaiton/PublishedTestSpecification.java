package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity_;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublishedTestSpecification {

    public static Specification<PublishedTestEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                cb.lower(root.join(PublishedTestEntity_.TEST_PATTERN).get(TestPatternEntity_.NAME)),
                like(name.toLowerCase())
        );
    }

    public static Specification<PublishedTestEntity> hasSubject(UUID subjectId) {
        if (subjectId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(PublishedTestEntity_.TEST_PATTERN)
                        .join(TestPatternEntity_.SUBJECT)
                        .get(BaseEntity_.ID),
                subjectId
        );
    }

    public static Specification<PublishedTestEntity> hasGroupId(UUID groupId) {
        if (groupId == null) {
            return null;
        }

        return (root, query, cb) -> cb.isMember(groupId, root.get(PublishedTestEntity_.GROUP_IDS));
    }

}

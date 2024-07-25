package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity_;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity_;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;
import static ru.kosterror.testsforge.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionSpecification {

    public static Specification<QuestionEntity> hasSubject(UUID subjectId) {
        if (subjectId == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(
                root.join(QuestionEntity_.SUBJECT)
                        .get(BaseEntity_.ID),
                subjectId
        );
    }

    public static Specification<QuestionEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                cb.lower(root.get(QuestionEntity_.NAME)),
                like(name.toLowerCase())
        );
    }

    public static Specification<QuestionEntity> hasTypeIn(List<QuestionType> types) {
        if (isEmpty(types)) {
            return null;
        }

        return (root, query, cb) -> root.get(QuestionEntity_.TYPE).in(types);
    }

}

package ru.kosterror.testsforge.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity_;

import static ru.kosterror.testsforge.coreservice.specificaiton.SpecificationConstants.like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartitionSpecification {

    public static Specification<PartitionEntity> hasNameLike(String name) {
        if (name == null) {
            return null;
        }

        return (root, query, cb) -> cb.like(
                cb.lower(root.get(PartitionEntity_.NAME)),
                like(name.toLowerCase())
        );
    }

}

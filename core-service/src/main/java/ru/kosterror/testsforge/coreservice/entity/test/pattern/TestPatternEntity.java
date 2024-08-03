package ru.kosterror.testsforge.coreservice.entity.test.pattern;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "test_pattern")
@Getter
@Setter
public class TestPatternEntity extends BaseEntity {

    private String name;

    private String description;

    @CreatedBy
    private UUID ownerId;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order")
    private List<PartitionEntity> partitions;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

}

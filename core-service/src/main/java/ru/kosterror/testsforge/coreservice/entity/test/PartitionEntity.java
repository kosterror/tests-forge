package ru.kosterror.testsforge.coreservice.entity.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "partition")
@Getter
@Setter
public class PartitionEntity extends BaseEntity {

    private String name;

    private String description;

    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "test_pattern_id")
    private TestPatternEntity test;

    @OneToMany(mappedBy = "partition", cascade = CascadeType.ALL)
    @OrderBy("order")
    private List<BlockEntity> blocks;

}

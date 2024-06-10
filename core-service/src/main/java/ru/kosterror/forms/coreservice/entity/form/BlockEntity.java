package ru.kosterror.forms.coreservice.entity.form;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;
import ru.kosterror.forms.coreservice.entity.BlockType;

@Entity
@Table(name = "block")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class BlockEntity extends BaseEntity {

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private BlockType type;

    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "partition_id")
    private PartitionEntity partition;


}

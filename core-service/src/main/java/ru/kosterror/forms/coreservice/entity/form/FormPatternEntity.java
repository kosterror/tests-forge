package ru.kosterror.forms.coreservice.entity.form;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import ru.kosterror.forms.coreservice.entity.BaseEntity;
import ru.kosterror.forms.coreservice.entity.subject.SubjectEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "form")
@Getter
@Setter
public class FormPatternEntity extends BaseEntity {

    private String name;

    private String description;

    @CreatedBy
    private UUID ownerId;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order")
    private List<PartitionEntity> partitions;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

}

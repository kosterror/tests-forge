package ru.kosterror.testsforge.coreservice.entity.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity;
import ru.kosterror.testsforge.coreservice.entity.test.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.VariantEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class QuestionEntity extends BaseEntity {

    private String name;

    @Column(name = "\"order\"")
    private Integer order;

    @CreatedBy
    private UUID ownerId;

    @Enumerated(value = EnumType.STRING)
    private QuestionType type;

    @ElementCollection
    @CollectionTable(name = "question_attachment", joinColumns = @JoinColumn(name = "question_id"))
    private List<UUID> attachments;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private VariantEntity variant;

    @ManyToOne
    @JoinColumn(name = "dynamic_block_id")
    private DynamicBlockEntity dynamicBlock;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

}

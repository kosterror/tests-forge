package ru.kosterror.testsforge.coreservice.entity.test.generated;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "generated_test")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTestEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private GeneratedTestStatus status;

    @ManyToOne
    @JoinColumn(name = "published_test_id")
    private PublishedTestEntity publishedTest;

    private UUID userId;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Partition> partitions;

    private Integer points;

}

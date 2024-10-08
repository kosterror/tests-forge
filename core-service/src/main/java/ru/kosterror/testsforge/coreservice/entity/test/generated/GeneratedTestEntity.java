package ru.kosterror.testsforge.coreservice.entity.test.generated;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "test_generated")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTestEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private TestStatus status;

    @ManyToOne
    @JoinColumn(name = "published_test_id")
    private PublishedTestEntity publishedTest;

    private UUID userId;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Partition> partitions;

    private Integer points;

    private String mark;

    private LocalDateTime submitDateTime;

}

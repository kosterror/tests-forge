package ru.kosterror.testsforge.coreservice.entity.test.published;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "test_published")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishedTestEntity extends BaseEntity {

    private LocalDateTime deadline;

    private Integer timer;

    private boolean showPointsToStudents;

    @Column(name = "group_id")
    @ElementCollection
    @CollectionTable(name = "group_id", joinColumns = @JoinColumn(name = "test_published_id"))
    private List<UUID> groupIds;

    @Column(name = "user_id")
    @ElementCollection
    @CollectionTable(name = "user_id", joinColumns = @JoinColumn(name = "test_published_id"))
    private List<UUID> userIds;

    @ManyToOne
    @JoinColumn(name = "test_pattern_id")
    private TestPatternEntity testPattern;

    @OneToMany(mappedBy = "publishedTest")
    private List<GeneratedTestEntity> generatedTests;

    private Boolean isNeedPostModeration;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<Integer, String> markConfiguration;

}

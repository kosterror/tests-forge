package ru.kosterror.testsforge.coreservice.entity.test;

import jakarta.persistence.*;
import lombok.*;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;
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

    @ElementCollection
    @CollectionTable(name = "group_id", joinColumns = @JoinColumn(name = "test_published_id"))
    private List<UUID> groupIds;

    @ElementCollection
    @CollectionTable(name = "user_id", joinColumns = @JoinColumn(name = "test_published_id"))
    private List<UUID> userIds;

    @ManyToOne
    @JoinColumn(name = "test_pattern_id")
    private TestPatternEntity formPattern;
}

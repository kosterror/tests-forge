package ru.kosterror.testsforge.coreservice.entity.test.generated;

import jakarta.persistence.*;
import lombok.*;
import ru.kosterror.testsforge.coreservice.entity.BaseEntity;
import ru.kosterror.testsforge.coreservice.entity.converter.DynamicBlockLinksConverter;
import ru.kosterror.testsforge.coreservice.entity.converter.StaticBlockLinksConverter;
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

    @Convert(converter = DynamicBlockLinksConverter.class)
    @Column(columnDefinition = "json")
    private List<DynamicBlockLink> dynamicBlockLinks;

    @Convert(converter = StaticBlockLinksConverter.class)
    @Column(columnDefinition = "json")
    private List<StaticBlockLink> staticBlockLinks;

}

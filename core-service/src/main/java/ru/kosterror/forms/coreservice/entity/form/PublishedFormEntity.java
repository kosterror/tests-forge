package ru.kosterror.forms.coreservice.entity.form;

import jakarta.persistence.*;
import lombok.*;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "published_form")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishedFormEntity extends BaseEntity {

    private LocalDateTime deadline;

    private Integer timer;

    @ElementCollection
    @CollectionTable(name = "group_id", joinColumns = @JoinColumn(name = "published_form_id"))
    private List<UUID> groupIds;

    @ElementCollection
    @CollectionTable(name = "user_id", joinColumns = @JoinColumn(name = "published_form_id"))
    private List<UUID> userIds;

    @ManyToOne
    @JoinColumn(name = "form_pattern_id")
    private FormPatternEntity formPattern;
}

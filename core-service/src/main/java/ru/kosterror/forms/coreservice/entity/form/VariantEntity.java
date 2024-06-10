package ru.kosterror.forms.coreservice.entity.form;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "variant")
@Getter
@Setter
public class VariantEntity extends BaseEntity {

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private StaticBlockEntity block;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    @OrderBy("order")
    private List<QuestionEntity> questions;

}

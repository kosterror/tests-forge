package ru.kosterror.forms.coreservice.entity.form;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;

import java.util.List;

@Entity
@Table(name = "dynamic_block")
@Getter
@Setter
public class DynamicBlockEntity extends BlockEntity {

    @OneToMany(mappedBy = "dynamicBlock", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

}

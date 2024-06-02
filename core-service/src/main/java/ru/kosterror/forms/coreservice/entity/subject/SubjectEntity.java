package ru.kosterror.forms.coreservice.entity.subject;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.forms.coreservice.entity.BaseEntity;

@Entity
@Table(name = "subject")
@Getter
@Setter
public class SubjectEntity extends BaseEntity {

    private String name;

}

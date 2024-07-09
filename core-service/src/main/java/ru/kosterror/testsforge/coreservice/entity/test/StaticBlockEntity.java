package ru.kosterror.testsforge.coreservice.entity.test;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "static_block")
@Getter
@Setter
public class StaticBlockEntity extends BlockEntity {

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<VariantEntity> variants;

}

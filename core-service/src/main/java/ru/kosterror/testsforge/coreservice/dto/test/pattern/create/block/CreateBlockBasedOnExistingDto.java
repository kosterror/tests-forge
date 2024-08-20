package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateBlockBasedOnExistingDto extends CreateBlockDto {

    private UUID blockId;

    protected CreateBlockBasedOnExistingDto() {
        super(NewBlockType.BASED_ON_EXISTING);
    }

}

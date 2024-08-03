package ru.kosterror.testsforge.coreservice.entity.test;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DynamicBlockLink {

    private UUID blockId;

    private List<UUID> questionIds;

}

package ru.kosterror.testsforge.coreservice.service.block;

import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

import java.util.UUID;

public interface BlockService {

    BlockEntity getBlockEntityById(UUID id);

}

package ru.kosterror.testsforge.coreservice.service.block;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

import java.util.UUID;

public interface BlockService {

    BlockEntity getBlockEntityById(UUID id);

    BlockDto getBlock(UUID id);

    PaginationResponse<BlockDto> getBlocks(String name, int page, int size);
}

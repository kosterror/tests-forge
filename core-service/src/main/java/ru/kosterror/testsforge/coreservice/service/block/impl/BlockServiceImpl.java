package ru.kosterror.testsforge.coreservice.service.block.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.repository.BlockRepository;
import ru.kosterror.testsforge.coreservice.service.block.BlockService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

    @Override
    public BlockEntity getBlockEntityById(UUID id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block with id %s not found".formatted(id)));
    }

}

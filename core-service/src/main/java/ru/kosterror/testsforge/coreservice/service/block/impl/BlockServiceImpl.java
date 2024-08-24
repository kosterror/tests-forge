package ru.kosterror.testsforge.coreservice.service.block.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity_;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.block.BlockMapper;
import ru.kosterror.testsforge.coreservice.repository.BlockRepository;
import ru.kosterror.testsforge.coreservice.service.block.BlockService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.BlockSpecification.hasNameLike;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    @Override
    public BlockEntity getBlockEntityById(UUID id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block with id %s not found".formatted(id)));
    }

    @Override
    public BlockDto getBlock(UUID id) {
        return blockMapper.toDto(getBlockEntityById(id));
    }

    @Override
    public PaginationResponse<BlockDto> getBlocks(String name, int page, int size) {
        var specification = Specification.<BlockEntity>where(null)
                .and(hasNameLike(name));

        var pageable = PageRequest.of(page, size, Sort.Direction.ASC, BlockEntity_.NAME);

        var blocks = blockRepository.findAll(specification, pageable)
                .stream()
                .map(blockMapper::toDto)
                .toList();

        return new PaginationResponse<>(page, size, blocks);
    }

}

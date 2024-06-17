package ru.kosterror.forms.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BaseFormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.forms.coreservice.entity.form.FormPatternEntity;
import ru.kosterror.forms.coreservice.exception.NotFoundException;
import ru.kosterror.forms.coreservice.mapper.FormPatternMapper;
import ru.kosterror.forms.coreservice.repository.FormPatternRepository;
import ru.kosterror.forms.coreservice.service.FormPatternService;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.specificaiton.FormPatternSpecification.*;

@Service
@RequiredArgsConstructor
public class FormPatternServiceImpl implements FormPatternService {

    private final FormPatternRepository formPatternRepository;
    private final FormPatternMapper formPatternMapper;

    @Override
    @Transactional
    public FormPatternDto createFormPattern(UUID userId, UpdateFormPatternDto updateFormPatternDto) {
        var form = formPatternMapper.toEntity(updateFormPatternDto);
        form = formPatternRepository.save(form);

        return formPatternMapper.toDto(form);
    }

    @Override
    public FormPatternDto getFormPattern(UUID id) {
        var form = getFormPatternEntity(id);

        return formPatternMapper.toDto(form);
    }

    @Override
    public FormPatternEntity getFormPatternEntity(UUID id) {
        return formPatternRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form pattern with id %s not found".formatted(id)));
    }

    @Override
    public PaginationResponse<BaseFormPatternDto> getFormPatterns(int page,
                                                                  int size,
                                                                  String name,
                                                                  UUID ownerId,
                                                                  UUID subjectId
    ) {
        var specification = Specification.<FormPatternEntity>where(null)
                .and(hasNameLike(name))
                .and(hasOwner(ownerId))
                .and(hasSubject(subjectId))
                .and(orderByName());

        var formPage = formPatternRepository.findAll(specification, PageRequest.of(page, size));

        var formDtos = formPage.getContent().stream()
                .map(formPatternMapper::toBaseDto)
                .toList();

        return new PaginationResponse<>(page, size, formDtos);
    }

}

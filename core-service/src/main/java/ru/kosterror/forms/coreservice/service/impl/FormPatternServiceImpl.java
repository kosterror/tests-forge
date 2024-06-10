package ru.kosterror.forms.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.forms.coreservice.exception.NotFoundException;
import ru.kosterror.forms.coreservice.mapper.FormPatternMapper;
import ru.kosterror.forms.coreservice.repository.FormPatternRepository;
import ru.kosterror.forms.coreservice.service.FormPatternService;

import java.util.UUID;

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
        var form = formPatternRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form with id %s not found".formatted(id)));

        return formPatternMapper.toDto(form);
    }

}

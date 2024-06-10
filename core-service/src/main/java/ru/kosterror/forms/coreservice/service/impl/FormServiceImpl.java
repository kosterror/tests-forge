package ru.kosterror.forms.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.forms.coreservice.dto.form.createupdate.CreateFormDto;
import ru.kosterror.forms.coreservice.dto.form.full.FormDto;
import ru.kosterror.forms.coreservice.exception.NotFoundException;
import ru.kosterror.forms.coreservice.mapper.FormMapper;
import ru.kosterror.forms.coreservice.repository.FormRepository;
import ru.kosterror.forms.coreservice.service.FormService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final FormMapper formMapper;

    @Override
    @Transactional
    public FormDto createFormPattern(UUID userId, CreateFormDto createFormDto) {
        var form = formMapper.toEntity(createFormDto);
        form = formRepository.save(form);

        return formMapper.toDto(form);
    }

    @Override
    public FormDto getFormPattern(UUID id) {
        var form = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form with id %s not found".formatted(id)));

        return formMapper.toDto(form);
    }

}

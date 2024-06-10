package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.form.createupdate.CreateFormDto;
import ru.kosterror.forms.coreservice.dto.form.full.FormDto;

import java.util.UUID;

public interface FormService {

    FormDto createFormPattern(UUID userId, CreateFormDto createFormDto);

    FormDto getFormPattern(UUID id);

}

package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.subject.CreateUpdateSubjectDto;
import ru.kosterror.forms.coreservice.dto.subject.SubjectDto;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    SubjectDto createSubject(CreateUpdateSubjectDto subjectDto);

    SubjectDto getSubject(UUID id);

    List<SubjectDto> findSubjects(String name);

    SubjectDto updateSubject(UUID id, CreateUpdateSubjectDto subjectDto);

    void deleteSubject(UUID id);
}

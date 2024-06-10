package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.subject.SubjectDto;
import ru.kosterror.forms.coreservice.dto.subject.UpdateSubjectDto;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    SubjectDto createSubject(UpdateSubjectDto subjectDto);

    SubjectDto getSubject(UUID id);

    List<SubjectDto> findSubjects(String name);

    SubjectDto updateSubject(UUID id, UpdateSubjectDto subjectDto);

    void deleteSubject(UUID id);
}

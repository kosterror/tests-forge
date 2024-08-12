package ru.kosterror.testsforge.coreservice.service.subject;

import ru.kosterror.testsforge.coreservice.dto.subject.SubjectDto;
import ru.kosterror.testsforge.coreservice.dto.subject.UpdateSubjectDto;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    SubjectDto createSubject(UpdateSubjectDto subjectDto);

    SubjectDto getSubject(UUID id);

    SubjectEntity getSubjectEntity(UUID id);

    List<SubjectDto> findSubjects(String name);

    SubjectDto updateSubject(UUID id, UpdateSubjectDto subjectDto);

    void deleteSubject(UUID id);
}

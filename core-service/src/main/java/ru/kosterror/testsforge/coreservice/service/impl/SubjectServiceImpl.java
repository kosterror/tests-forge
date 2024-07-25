package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.subject.SubjectDto;
import ru.kosterror.testsforge.coreservice.dto.subject.UpdateSubjectDto;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity_;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;
import ru.kosterror.testsforge.coreservice.repository.SubjectRepository;
import ru.kosterror.testsforge.coreservice.service.SubjectService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository repository;
    private final SubjectMapper mapper;

    @Override
    public SubjectDto createSubject(UpdateSubjectDto subjectDto) {
        var subjectEntity = mapper.toEntity(subjectDto);
        subjectEntity = repository.save(subjectEntity);

        return mapper.toDto(subjectEntity);
    }

    @Override
    public SubjectDto getSubject(UUID id) {
        var subjectEntity = getSubjectById(id);
        return mapper.toDto(subjectEntity);
    }

    @Override
    public SubjectEntity getSubjectEntity(UUID id) {
        return getSubjectById(id);
    }

    @Override
    public List<SubjectDto> findSubjects(String name) {
        var subjects = name == null
                ? repository.findAll(Sort.by(Sort.Direction.ASC, SubjectEntity_.NAME))
                : repository.findAllByNameContainingIgnoreCaseOrderByName(name);

        return subjects.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SubjectDto updateSubject(UUID id, UpdateSubjectDto subjectDto) {
        var subjectEntity = getSubjectById(id);
        subjectEntity.setName(subjectDto.name());
        subjectEntity = repository.save(subjectEntity);

        return mapper.toDto(subjectEntity);
    }

    @Override
    public void deleteSubject(UUID id) {
        var subjectEntity = getSubjectById(id);
        repository.delete(subjectEntity);
    }

    private SubjectEntity getSubjectById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject %s not found".formatted(id)));
    }

}

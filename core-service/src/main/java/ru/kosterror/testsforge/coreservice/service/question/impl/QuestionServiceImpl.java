package ru.kosterror.testsforge.coreservice.service.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.client.FileStorageClient;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;
import ru.kosterror.testsforge.coreservice.repository.QuestionRepository;
import ru.kosterror.testsforge.coreservice.service.question.QuestionService;
import ru.kosterror.testsforge.coreservice.service.subject.SubjectService;

import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.QuestionSpecification.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final FileStorageClient fileStorageClient;
    private final SubjectService subjectService;

    @Override
    public QuestionDto createQuestion(UUID userId, UUID subjectId, CreateQuestionDto question) {
        var subject = subjectService.getSubjectEntity(subjectId);

        validateAttachments(userId, question.getAttachments());
        log.info("Attachments for question {} validated", question);

        var entity = questionMapper.toEntity(question);
        entity.setOwnerId(userId);
        entity.setSubject(subject);

        entity = questionRepository.save(entity);
        log.info("Question with id {} created", entity.getId());

        return questionMapper.toDto(entity);
    }

    @Override
    public QuestionDto getQuestion(UUID id) {
        var entity = getQuestionById(id);
        return questionMapper.toDto(entity);
    }

    @Override
    public void deleteQuestion(UUID id) {
        var entity = getQuestionById(id);
        questionRepository.delete(entity);
    }

    @Override
    public PaginationResponse<QuestionDto> getQuestions(UUID subjectId,
                                                        String name,
                                                        List<QuestionType> types,
                                                        int page,
                                                        int size
    ) {
        var specification = Specification.<QuestionEntity>where(null)
                .and(hasSubject(subjectId))
                .and(hasNameLike(name))
                .and(hasTypeIn(types));

        var pageable = PageRequest.of(page, size, Sort.Direction.ASC, QuestionEntity_.NAME);

        var questions = questionRepository.findAll(specification, pageable).getContent()
                .stream()
                .map(questionMapper::toDto)
                .toList();

        return new PaginationResponse<>(page, size, questions);
    }

    private QuestionEntity getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
    }

    private void validateAttachments(UUID userId, List<UUID> attachmentIds) {
        if (CollectionUtils.isEmpty(attachmentIds)) {
            return;
        }

        for (var attachmentId : attachmentIds) {
            var fileMetaInfo = fileStorageClient.getFileMetaInfo(attachmentId);

            if (fileMetaInfo.ownerId() != userId) {
                throw new BadRequestException("File '%s' not owned by user '%s'".formatted(attachmentId, userId));
            }
        }
    }

}

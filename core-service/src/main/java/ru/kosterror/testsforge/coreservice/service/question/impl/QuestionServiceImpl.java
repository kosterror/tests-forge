package ru.kosterror.testsforge.coreservice.service.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity_;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.ConflictException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.factory.question.QuestionFactory;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;
import ru.kosterror.testsforge.coreservice.repository.QuestionRepository;
import ru.kosterror.testsforge.coreservice.service.attachment.AttachmentService;
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
    private final SubjectService subjectService;
    private final QuestionFactory questionFactory;
    private final AttachmentService attachmentService;

    @Override
    public QuestionDto createQuestion(UUID subjectId, CreateQuestionDto questionDto) {
        var subject = subjectService.getSubjectEntity(subjectId);

        var entity = questionFactory.buildQuestionFromDto(questionDto);
        log.info("Question entity built from dto {}", questionDto);

        attachmentService.validateAttachments(entity.getAttachments());
        log.info("Attachments for question {} validated", questionDto);

        entity.setSubject(subject);
        entity.setQuestionFromBank(true);

        entity = questionRepository.save(entity);
        log.info("Question with id {} created", entity.getId());

        return questionMapper.toDto(entity);
    }

    @Override
    public QuestionDto getQuestion(UUID id) {
        var entity = getQuestionEntity(id);
        return questionMapper.toDto(entity);
    }

    @Override
    public void deleteQuestion(UUID id) {
        var entity = getQuestionEntity(id);

        if (!entity.isQuestionFromBank()) {
            throw new ConflictException("Question with id %s is not from question bank".formatted(id));
        }

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

    @Override
    public QuestionEntity getQuestionEntity(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
    }

}

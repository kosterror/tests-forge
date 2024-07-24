package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.client.FileStorageClient;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;
import ru.kosterror.testsforge.coreservice.repository.QuestionRepository;
import ru.kosterror.testsforge.coreservice.service.QuestionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final FileStorageClient fileStorageClient;

    @Override
    public QuestionDto createQuestion(UUID userId, CreateQuestionDto question) {
        if (question.getAttachments() != null && !question.getAttachments().isEmpty()) {
            validateAttachments(userId, question.getAttachments());
        }

        var entity = questionMapper.toEntity(question);
        entity.setOwnerId(userId);

        entity = questionRepository.save(entity);
        return questionMapper.toDto(entity);
    }

    @Override
    public QuestionDto getQuestion(UUID id) {
        var entity = getQuestionById(id);
        return questionMapper.toDto(entity);
    }

    private QuestionEntity getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
    }

    private void validateAttachments(UUID userId, List<UUID> attachmentIds) {
        for (var attachmentId : attachmentIds) {
            var fileMetaInfo = fileStorageClient.getFileMetaInfo(attachmentId);

            if (fileMetaInfo.ownerId() != userId) {
                throw new BadRequestException("File '%s' not owned by user '%s'".formatted(attachmentId, userId));
            }
        }
    }

}

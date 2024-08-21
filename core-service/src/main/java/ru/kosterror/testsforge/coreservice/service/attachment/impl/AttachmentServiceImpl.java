package ru.kosterror.testsforge.coreservice.service.attachment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kosterror.testsforge.coreservice.client.FileStorageClient;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.attachment.AttachmentService;
import ru.kosterror.testsforge.coreservice.service.util.TestUtilService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final FileStorageClient fileStorageClient;
    private final TestUtilService testUtilService;

    @Override
    public void validateAttachments(List<UUID> attachmentIds) {
        if (CollectionUtils.isEmpty(attachmentIds)) {
            return;
        }

        var notExistingFileIds = fileStorageClient.getNotExistingFileIds(attachmentIds);

        if (!notExistingFileIds.isEmpty()) {
            throw new NotFoundException("Attachments with ids %s not found".formatted(notExistingFileIds));
        }
    }

    @Override
    public void validateAttachments(TestPatternEntity testPattern) {
        var questions = testUtilService.extractQuestionEntities(testPattern);
        var attachmentIds = questions.stream()
                .map(QuestionEntity::getAttachments)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();

        validateAttachments(attachmentIds);
    }


}

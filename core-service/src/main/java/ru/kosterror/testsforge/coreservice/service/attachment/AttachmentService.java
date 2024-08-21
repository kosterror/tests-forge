package ru.kosterror.testsforge.coreservice.service.attachment;

import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    void validateAttachments(List<UUID> attachmentIds);

    void validateAttachments(TestPatternEntity testPattern);
}

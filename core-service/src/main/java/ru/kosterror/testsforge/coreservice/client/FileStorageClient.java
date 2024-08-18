package ru.kosterror.testsforge.coreservice.client;

import java.util.List;
import java.util.UUID;

public interface FileStorageClient {

    List<UUID> getNotExistingFileIds(List<UUID> fileIds);
}

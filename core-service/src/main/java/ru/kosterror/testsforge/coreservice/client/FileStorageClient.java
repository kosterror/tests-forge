package ru.kosterror.testsforge.coreservice.client;

import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;

import java.util.UUID;

public interface FileStorageClient {

    FileMetaInfoDto getFileMetaInfo(UUID id);

}

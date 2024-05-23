package ru.kosterror.forms.coreservice.client;

import ru.kosterror.forms.commonmodel.filestorageservice.FileMetaInfoDto;

import java.util.UUID;

public interface FileStorageClient {

    FileMetaInfoDto getFileMetaInfo(UUID id);

}

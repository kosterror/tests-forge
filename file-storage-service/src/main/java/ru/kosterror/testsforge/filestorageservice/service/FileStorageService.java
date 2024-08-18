package ru.kosterror.testsforge.filestorageservice.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;
import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;

import java.util.List;
import java.util.UUID;

public interface FileStorageService {

    FileMetaInfoDto uploadFile(UUID userId, MultipartFile file);

    Pair<String, byte[]> downloadFile(UUID fileId);

    FileMetaInfoDto getFileMetaInfo(UUID fileId);

    List<UUID> checkExistingFiles(List<UUID> fileIds);
}

package ru.kosterror.forms.filestorageservice.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;
import ru.kosterror.forms.filestorageservice.dto.FileMetaInfoDto;

import java.util.UUID;

public interface FileStorageService {

    FileMetaInfoDto uploadFile(UUID userId, MultipartFile file);

    Pair<String, byte[]> downloadFile(UUID fileId);

    FileMetaInfoDto getFileMetaInfo(UUID fileId);
}

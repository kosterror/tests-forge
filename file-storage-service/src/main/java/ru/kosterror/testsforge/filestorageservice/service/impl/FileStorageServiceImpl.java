package ru.kosterror.testsforge.filestorageservice.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;
import ru.kosterror.testsforge.filestorageservice.configuration.MinioProperties;
import ru.kosterror.testsforge.filestorageservice.entity.FileMetaInfoEntity;
import ru.kosterror.testsforge.filestorageservice.exception.InternalException;
import ru.kosterror.testsforge.filestorageservice.exception.NotFoundException;
import ru.kosterror.testsforge.filestorageservice.mapper.FileMetaInfoMapper;
import ru.kosterror.testsforge.filestorageservice.repository.FileMetaInfoRepository;
import ru.kosterror.testsforge.filestorageservice.service.FileStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileMetaInfoRepository fileMetaInfoRepository;
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final FileMetaInfoMapper fileMetaInfoMapper;

    @Override
    public FileMetaInfoDto uploadFile(UUID userId, MultipartFile file) {
        var fileMetaInfo = FileMetaInfoEntity.builder()
                .id(UUID.randomUUID())
                .bucket(minioProperties.bucket())
                .name(file.getOriginalFilename())
                .size((double) file.getSize() / 1024)
                .ownerId(userId)
                .uploadDateTime(LocalDateTime.now())
                .build();

        var putObjectArgs = getPutObjectArgs(file, fileMetaInfo);
        putObject(putObjectArgs);
        fileMetaInfo = fileMetaInfoRepository.save(fileMetaInfo);

        return fileMetaInfoMapper.toDto(fileMetaInfo);
    }

    @Override
    public Pair<String, byte[]> downloadFile(UUID fileId) {
        var fileMetaInfo = getFileMetaInfoEntity(fileId);

        var getObjectArgs = GetObjectArgs
                .builder()
                .bucket(fileMetaInfo.getBucket())
                .object(fileMetaInfo.getId().toString())
                .build();

        try (var in = minioClient.getObject(getObjectArgs)) {
            return Pair.of(fileMetaInfo.getName(), in.readAllBytes());
        } catch (Exception exception) {
            throw new InternalException("Error while downloading file", exception);
        }
    }

    @Override
    public FileMetaInfoDto getFileMetaInfo(UUID fileId) {
        var fileMetaInfo = getFileMetaInfoEntity(fileId);

        return fileMetaInfoMapper.toDto(fileMetaInfo);
    }

    private FileMetaInfoEntity getFileMetaInfoEntity(UUID fileId) {
        return fileMetaInfoRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File with id %s not found".formatted(fileId)));
    }

    private PutObjectArgs getPutObjectArgs(MultipartFile file, FileMetaInfoEntity fileMetaInfo) {
        PutObjectArgs putObjectArgs;
        try {
            putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.bucket())
                    .object(fileMetaInfo.getId().toString())
                    .stream(new ByteArrayInputStream(file.getBytes()), file.getSize(), -1)
                    .build();
        } catch (IOException exception) {
            throw new InternalException("Error while reading file", exception);
        }
        return putObjectArgs;
    }

    private void putObject(PutObjectArgs putObjectArgs) {
        try {
            minioClient.putObject(putObjectArgs);
        } catch (Exception exception) {
            throw new InternalException("Error while uploading file", exception);
        }
    }


}

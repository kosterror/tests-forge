package ru.kosterror.testsforge.coreservice.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;
import ru.kosterror.testsforge.coreservice.client.FileStorageClient;
import ru.kosterror.testsforge.coreservice.exception.InternalException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;

import java.util.UUID;

@Slf4j
@Component
public class FileStorageClientImpl implements FileStorageClient {

    private final RestClient restClient;

    public FileStorageClientImpl(@Qualifier("fileStorageClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public FileMetaInfoDto getFileMetaInfo(UUID id) {
        return restClient.get()
                .uri("/api/file-storage/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.equals(HttpStatus.NOT_FOUND),
                        (request, response) -> {
                            var responseBody = new String(response.getBody().readAllBytes());
                            log.error("Failed get file meta info {}, response: {}", id, responseBody);
                            throw new NotFoundException("File with id %s not found".formatted(id));
                        }
                ).onStatus(statusCode -> statusCode.is4xxClientError() && statusCode != HttpStatus.NOT_FOUND,
                        (request, response) -> {
                            var responseBody = new String(response.getBody().readAllBytes());
                            throw new InternalException("Failed get file meta info. Response: %s".formatted(responseBody));
                        }
                ).toEntity(FileMetaInfoDto.class)
                .getBody();
    }
}

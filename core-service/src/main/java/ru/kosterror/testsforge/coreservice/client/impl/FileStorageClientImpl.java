package ru.kosterror.testsforge.coreservice.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kosterror.testsforge.coreservice.client.FileStorageClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStorageClientImpl implements FileStorageClient {

    private final RestClient restClient;

    public FileStorageClientImpl(@Qualifier("fileStorageClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<UUID> getNotExistingFileIds(List<UUID> fileIds) {
        return restClient.post().uri("/api/files/check-existing")
                .body(fileIds)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<UUID>>() {
                }).getBody();
    }
}

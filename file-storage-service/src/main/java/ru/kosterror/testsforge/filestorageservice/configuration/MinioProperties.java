package ru.kosterror.testsforge.filestorageservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
        String accessKey,
        String secretKey,
        String bucket,
        String url
) {
}

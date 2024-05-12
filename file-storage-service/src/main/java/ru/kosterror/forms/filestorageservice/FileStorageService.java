package ru.kosterror.forms.filestorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FileStorageService {

    public static void main(String[] args) {
        SpringApplication.run(FileStorageService.class, args);
    }

}
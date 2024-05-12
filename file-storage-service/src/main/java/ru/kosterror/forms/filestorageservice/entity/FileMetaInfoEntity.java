package ru.kosterror.forms.filestorageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "file_meta_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaInfoEntity {

    @Id
    private UUID id;

    private String bucket;

    private String name;

    private double size;

    private UUID ownerId;

    private LocalDateTime uploadDateTime;

}

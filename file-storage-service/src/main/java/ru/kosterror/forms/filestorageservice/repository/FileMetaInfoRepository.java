package ru.kosterror.forms.filestorageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.filestorageservice.entity.FileMetaInfoEntity;

import java.util.UUID;

public interface FileMetaInfoRepository extends JpaRepository<FileMetaInfoEntity, UUID> {
}

package ru.kosterror.testsforge.filestorageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.filestorageservice.entity.FileMetaInfoEntity;

import java.util.UUID;

public interface FileMetaInfoRepository extends JpaRepository<FileMetaInfoEntity, UUID> {
}

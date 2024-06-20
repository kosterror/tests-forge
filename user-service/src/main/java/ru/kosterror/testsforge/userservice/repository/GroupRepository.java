package ru.kosterror.testsforge.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.userservice.entity.GroupEntity;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
}

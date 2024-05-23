package ru.kosterror.forms.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.userservice.entity.GroupEntity;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
}

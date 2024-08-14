package ru.kosterror.testsforge.coreservice.service.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    boolean isAnyGroupContainsUser(List<UUID> groupIds, UUID userId);

    List<String> getUserEmails(Collection<UUID> groupIds, Collection<UUID> userIds);

    Set<UUID> getUserIds(Collection<UUID> groupIds, Collection<UUID> userIds);

}

package ru.kosterror.testsforge.coreservice.service;

import java.util.List;
import java.util.UUID;

public interface UserService {

    boolean isAnyGroupContainsUser(List<UUID> groupIds, UUID userId);

}

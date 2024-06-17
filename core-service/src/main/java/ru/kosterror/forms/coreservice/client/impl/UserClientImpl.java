package ru.kosterror.forms.coreservice.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kosterror.forms.commonmodel.user.FoundGroupsDto;
import ru.kosterror.forms.commonmodel.user.FoundUsersDto;
import ru.kosterror.forms.coreservice.client.UserClient;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class UserClientImpl implements UserClient {

    private final RestClient restClient;

    public UserClientImpl(@Qualifier("userClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public FoundGroupsDto getGroupsByIds(Set<UUID> groupIds) {
        return restClient.get()
                .uri(builder -> builder
                        .path("/api/groups/search-by-ids")
                        .queryParam("groupIds", groupIds)
                        .build()
                ).retrieve()
                .toEntity(FoundGroupsDto.class)
                .getBody();
    }

    @Override
    public FoundUsersDto getUsersByIds(Set<UUID> userIds) {
        return restClient.get()
                .uri(builder -> builder
                        .path("/api/users/search-by-ids")
                        .queryParam("userIds", userIds)
                        .build()
                ).retrieve()
                .toEntity(FoundUsersDto.class)
                .getBody();
    }
}

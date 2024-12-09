package io.fresh.burger.auth.service.core.service;

import io.fresh.burger.auth.service.api.domain.enums.ClientRoleGroupName;
import io.fresh.burger.auth.service.core.domain.entity.ClientRoleGroup;
import io.fresh.burger.auth.service.core.exception.EntityNotFoundException;
import io.fresh.burger.auth.service.core.repository.ClientRoleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientRoleGroupService {
    private final ClientRoleGroupRepository clientRoleGroupRepository;

    public ClientRoleGroup findByName(ClientRoleGroupName clientRoleGroupName) {
        return clientRoleGroupRepository.findByClientRoleGroupName(clientRoleGroupName)
                .orElseThrow(() -> new EntityNotFoundException(
                        ClientRoleGroup.class, ClientRoleGroup.Fields.clientRoleGroupName, clientRoleGroupName
                ));
    }
}
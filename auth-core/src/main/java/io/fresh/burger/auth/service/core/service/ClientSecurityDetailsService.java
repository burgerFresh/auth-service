package io.fresh.burger.auth.service.core.service;

import io.fresh.burger.auth.service.api.domain.dto.AuthRequest;
import io.fresh.burger.auth.service.api.domain.enums.ClientRoleGroupName;
import io.fresh.burger.auth.service.core.builder.RoleGroupSecurityBuilder;
import io.fresh.burger.auth.service.core.domain.dto.ClientSecurityDetailsDto;
import io.fresh.burger.auth.service.core.domain.entity.ClientSecurityDetails;
import io.fresh.burger.auth.service.core.exception.EntityNotFoundException;
import io.fresh.burger.auth.service.core.mapper.ClientSecurityDetailsMapper;
import io.fresh.burger.auth.service.core.repository.ClientSecurityDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientSecurityDetailsService implements UserDetailsService {
    private final ClientSecurityDetailsRepository clientSecurityDetailsRepository;
    private final ClientSecurityDetailsMapper clientSecurityDetailsMapper;
    private final ClientRoleGroupService clientRoleGroupService;

    @Override
    public UserDetails loadUserByUsername(String clientUuid) throws UsernameNotFoundException {
        return findByClientUuid(UUID.fromString(clientUuid));
    }

    public ClientSecurityDetailsDto findByLogin(String login) {
        return clientSecurityDetailsRepository.findByLogin(login)
                .map(clientSecurityDetailsMapper::toClientSecurityDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        ClientSecurityDetails.class, ClientSecurityDetails.Fields.login, login
                ));
    }

    public ClientSecurityDetailsDto findByClientUuid(UUID clientUuid) {
        return clientSecurityDetailsRepository.findByClientUuid(clientUuid)
                .map(clientSecurityDetailsMapper::toClientSecurityDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        ClientSecurityDetails.class, ClientSecurityDetails.Fields.clientUuid, clientUuid
                ));
    }

    @Transactional
    public ClientSecurityDetailsDto create(AuthRequest authRequest) {
        var clientSecurityDetails = clientSecurityDetailsMapper.toClientSecurityDetails(authRequest);
        addRoleGroupToNewClient(clientSecurityDetails);
        clientSecurityDetailsRepository.save(clientSecurityDetails);
        return clientSecurityDetailsMapper.toClientSecurityDetailsDto(clientSecurityDetails);
    }

    public UUID findUuidByLogin(String login) {
        return clientSecurityDetailsRepository.findUuidByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(
                        ClientSecurityDetails.class, ClientSecurityDetails.Fields.login, login
                ));
    }

    private void addRoleGroupToNewClient(ClientSecurityDetails clientSecurityDetails) {
        var clientRoleGroup = clientRoleGroupService.findByName(ClientRoleGroupName.CLIENT);
        var roleGroupSecurity = RoleGroupSecurityBuilder.build(clientSecurityDetails, clientRoleGroup);
        clientSecurityDetails.getRoleGroupSecurities().add(roleGroupSecurity);
    }

}
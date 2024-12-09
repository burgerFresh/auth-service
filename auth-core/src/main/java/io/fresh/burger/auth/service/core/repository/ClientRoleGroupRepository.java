package io.fresh.burger.auth.service.core.repository;

import io.fresh.burger.auth.service.api.domain.enums.ClientRoleGroupName;
import io.fresh.burger.auth.service.core.domain.entity.ClientRoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRoleGroupRepository extends JpaRepository<ClientRoleGroup, Long> {

    Optional<ClientRoleGroup> findByClientRoleGroupName(ClientRoleGroupName clientRoleGroupName);
}
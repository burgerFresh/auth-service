package io.fresh.burger.auth.service.core.repository;

import io.fresh.burger.auth.service.core.domain.entity.ClientSecurityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientSecurityDetailsRepository extends JpaRepository<ClientSecurityDetails, Long> {

    Optional<ClientSecurityDetails> findByLogin(String login);

    Optional<ClientSecurityDetails> findByClientUuid(UUID clientUuid);

    @Query("select d.clientUuid from ClientSecurityDetails d where d.login = :login")
    Optional<UUID> findUuidByLogin(String login);
}
package io.fresh.burger.auth.service.core.domain.entity;

import io.fresh.burger.auth.service.api.domain.enums.ClientRoleName;
import io.fresh.burger.auth.service.core.util.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@SQLDelete(sql = AppConstants.DELETE_CLIENT_ROLE)
public class ClientRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientRoleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ClientRoleName clientRoleName;

    @Column(nullable = false)
    private Boolean isActual;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_role_group_id", nullable = false)
    private ClientRoleGroup clientRoleGroup;
}
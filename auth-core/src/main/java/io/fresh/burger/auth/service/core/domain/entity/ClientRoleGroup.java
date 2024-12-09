package io.fresh.burger.auth.service.core.domain.entity;

import io.fresh.burger.auth.service.api.domain.enums.ClientRoleGroupName;
import io.fresh.burger.auth.service.core.util.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

@Data
@Entity
@SQLDelete(sql = AppConstants.DELETE_CLIENT_ROLE_GROUP)
@FieldNameConstants
public class ClientRoleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientRoleGroupId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ClientRoleGroupName clientRoleGroupName;

    @Column(nullable = false)
    private Boolean isActual;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientRoleGroup")
    private Set<ClientRole> clientRoles;
}

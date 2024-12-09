package io.fresh.burger.auth.service.core.builder;

import io.fresh.burger.auth.service.core.domain.entity.ClientRoleGroup;
import io.fresh.burger.auth.service.core.domain.entity.ClientSecurityDetails;
import io.fresh.burger.auth.service.core.domain.entity.RoleGroupSecurity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleGroupSecurityBuilder {

    public static RoleGroupSecurity build(ClientSecurityDetails clientSecurityDetails, ClientRoleGroup clientRoleGroup) {
        return RoleGroupSecurity.builder()
                .clientSecurityDetails(clientSecurityDetails)
                .clientRoleGroup(clientRoleGroup)
                .build();
    }
}
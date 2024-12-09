package io.fresh.burger.auth.service.core.mapper;

import io.fresh.burger.auth.service.api.domain.dto.AuthRequest;
import io.fresh.burger.auth.service.api.domain.enums.ClientRoleGroupName;
import io.fresh.burger.auth.service.core.domain.dto.ClientSecurityDetailsDto;
import io.fresh.burger.auth.service.core.domain.dto.RoleDto;
import io.fresh.burger.auth.service.core.domain.entity.ClientRoleGroup;
import io.fresh.burger.auth.service.core.domain.entity.ClientSecurityDetails;
import io.fresh.burger.auth.service.core.domain.entity.RoleGroupSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientSecurityDetailsMapper {

    @Mapping(target = "authorities", expression = "java(mapAuthorities(source))")
    ClientSecurityDetailsDto toClientSecurityDetailsDto(ClientSecurityDetails source);

    @Mapping(target = "clientSecurityDetailsId", ignore = true)
//    @Mapping(target = "password", ignore = true)
    @Mapping(target = "clientUuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "deletedDate", ignore = true)
    @Mapping(target = "roleGroupSecurities", ignore = true)
    ClientSecurityDetails toClientSecurityDetails(AuthRequest authRequest);

    default List<RoleDto> mapAuthorities(ClientSecurityDetails source) {
        return source.getRoleGroupSecurities().stream()
                .map(RoleGroupSecurity::getClientRoleGroup)
                .map(ClientRoleGroup::getClientRoleGroupName)
                .map(ClientRoleGroupName::name)
                .map(RoleDto::new)
                .toList();
    }
}
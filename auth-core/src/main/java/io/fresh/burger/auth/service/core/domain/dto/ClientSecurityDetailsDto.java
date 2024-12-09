package io.fresh.burger.auth.service.core.domain.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@NoArgsConstructor
public class ClientSecurityDetailsDto implements UserDetails {

    private UUID clientUuid;
    private List<RoleDto> authorities;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return clientUuid.toString();
    }
}
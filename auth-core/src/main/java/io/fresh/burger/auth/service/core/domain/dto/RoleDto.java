package io.fresh.burger.auth.service.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements GrantedAuthority {

    private String authorities;

    @Override
    public String getAuthority() {
        return authorities;
    }
}

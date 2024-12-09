package io.fresh.burger.auth.service.core.domain.entity;

import io.fresh.burger.auth.service.core.util.AppConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@FieldNameConstants
@SQLDelete(sql = AppConstants.DELETE_CLIENT_SECURITY_INFO)
public class ClientSecurityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientSecurityDetailsId;

    @Column(nullable = false, unique = true)
    private UUID clientUuid;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    private LocalDate deletedDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientSecurityDetails", cascade = CascadeType.PERSIST)
    private Set<RoleGroupSecurity> roleGroupSecurities = new HashSet<>();
}
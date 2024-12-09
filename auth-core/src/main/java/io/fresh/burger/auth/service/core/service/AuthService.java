package io.fresh.burger.auth.service.core.service;

import io.fresh.burger.auth.service.api.domain.dto.AuthRequest;
import io.fresh.burger.auth.service.core.domain.dto.ClientSecurityDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    @Value("${jwt.expiration}")
    private Long jwtExpirationSec;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientSecurityDetailsService securityDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService service;

    public ResponseEntity<String> login(AuthRequest authRequest) {
        var uuid = securityDetailsService.findUuidByLogin(authRequest.login());
        UserDetails userDetails = service.loadUserByUsername(uuid.toString());
        String password = userDetails.getPassword();
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(uuid, authRequest.password());
        var authentication = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var clientSecurityDetailsDto = securityDetailsService.findByLogin(authRequest.login());
        var roles = extractRoles(clientSecurityDetailsDto);

        var token = jwtService.generateToken(clientSecurityDetailsDto.getUsername(), roles);
        var jwtCookie = buildResponseCookie(token);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Login successful");
    }

    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequest authRequest) {
        var encodedPassword = passwordEncoder.encode(authRequest.password());
        var encodedRequest = new AuthRequest(authRequest.login(), encodedPassword);
        var clientSecurityDetailsDto = securityDetailsService.create(encodedRequest);
        var roles = extractRoles(clientSecurityDetailsDto);

        var token = jwtService.generateToken(clientSecurityDetailsDto.getUsername(), roles);
        var jwtCookie = buildResponseCookie(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Client registered");
    }

    private ResponseCookie buildResponseCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtExpirationSec)
                .sameSite("Strict")
                .build();
    }

    private List<String> extractRoles(ClientSecurityDetailsDto clientSecurityDetailsDto) {
        return clientSecurityDetailsDto.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
package io.fresh.burger.auth.service.core.controller;

import io.fresh.burger.auth.service.api.domain.dto.AuthRequest;
import io.fresh.burger.auth.service.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.fresh.burger.auth.service.core.util.AppConstants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequest authRequest) {
        return authService.register(authRequest);
    }

}
package io.fresh.burger.auth.service.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "yyyy")
        @Size(max = 20, min = 4)
        String login,
        @NotBlank
        @Size(max = 20, min = 5)
        String password
) {
}
package io.fresh.burger.auth.service.core.exception.error.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String contextPath,
        LocalDateTime timestamp,
        Integer statusCode,
        String message) {
}
package io.fresh.burger.auth.service.core.exception.handler;

import io.fresh.burger.auth.service.core.exception.EntityNotFoundException;
import io.fresh.burger.auth.service.core.exception.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNoSuchElement(EntityNotFoundException ex, WebRequest request) {
        var requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        var errorResponse = new ErrorResponse(requestURI, LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
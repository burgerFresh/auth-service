package io.fresh.burger.auth.service.core.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> clazz, String errorFieldName, Object errorFieldValue) {
        super(String.format("No value present. Entity: %s, field: %s, value: %s",
                clazz.getSimpleName(), errorFieldName, errorFieldValue));
    }
}
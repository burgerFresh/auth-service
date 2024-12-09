package io.fresh.burger.auth.service.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final Algorithm algorithm; // Алгоритм для подписи токена
    private final JWTVerifier verifier; // Объект для проверки токена
    private final long jwtExpiration; // Время жизни токена в миллисекундах

    public JwtService(@Value("${jwt.secret}") String jwtSecret,
                      @Value("${jwt.expiration}") long jwtExpiration) {
        this.algorithm = Algorithm.HMAC512(jwtSecret); // Алгоритм HMAC512 с использованием секретного ключа
        this.verifier = JWT.require(algorithm).build(); // Настройка JWTVerifier
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * Генерация JWT токена
     */
    public String generateToken(String uuid, List<String> roles) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(uuid) // Устанавливаем subject
                .withClaim("roles", roles) // Добавляем кастомный клейм (роль)
                .withIssuedAt(Date.from(now)) // Время создания токена
                .withExpiresAt(Date.from(now.plusSeconds(jwtExpiration))) // Время истечения токена
                .sign(algorithm); // Подписываем токен
    }

    /**
     * Извлечение имени пользователя из токена
     */
    public String extractClientUuid(String token) {
        return decodeToken(token).getSubject();
    }

    /**
     * Извлечение роли пользователя из токена
     */
    public List<String> extractRoles(String token) {
        return decodeToken(token).getClaim("roles").asList(String.class);
    }

    /**
     * Проверка валидности токена
     */
    public boolean validateToken(String token) {
        try {
            var decodedJWT = decodeToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Декодирование токена для извлечения клеймов
     */
    private DecodedJWT decodeToken(String token) {
        return verifier.verify(token); // Парсим и проверяем токен
    }
}
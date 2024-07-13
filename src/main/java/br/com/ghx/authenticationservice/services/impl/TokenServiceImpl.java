package br.com.ghx.authenticationservice.services.impl;

import br.com.ghx.authenticationservice.constants.TokenServiceConstants;
import br.com.ghx.authenticationservice.domain.User;
import br.com.ghx.authenticationservice.services.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {

    public static final String OFFSET_ID = "-03:00";

    @Value(TokenServiceConstants.API_SECURITY_TOKEN_SECRET)
    private String secret;

    @Override
    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(TokenServiceConstants.AUTHENTICATION_SERVICE)
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(TokenServiceConstants.ERRO_AO_GERAR_TOKEN, exception);
        }
    }

    @Override
    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Authentication Service")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTCreationException exception) {
            throw new RuntimeException(TokenServiceConstants.INVALID_OR_EXPIRED_JWT_TOKEN);
        }
    }


    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.of(OFFSET_ID));
    }
}

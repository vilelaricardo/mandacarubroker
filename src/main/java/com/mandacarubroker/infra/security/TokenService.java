package com.mandacarubroker.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mandacarubroker.domain.authuser.AuthUser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;

/**
 * The generate token class.
 **/
@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * The method create a token for the requests.
   *
   * @param user The auth user data
   *
   **/
  public String generateSystemToken(AuthUser user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("mandacaru-broker-api")
          .withSubject(user.getLogin())
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException e) {
      throw new JWTCreationException("Error while generating system token", e);
    }
  }

  /**
   * The method validate the request token.
   *
   * @param token The request token
   *
   **/
  public String validateToken(String token) {
    String result;
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      result = JWT.require(algorithm)
          .withIssuer("mandacaru-broker-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      result = "";
    }

    return result;
  }

  private Instant generateExpirationDate() {
    return LocalDateTime
        .now()
        .plusMinutes(30)
        .toInstant(ZoneOffset.of("-03:00"));
  }
}

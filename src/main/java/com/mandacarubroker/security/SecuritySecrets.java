package com.mandacarubroker.security;

public final class SecuritySecrets {
    private SecuritySecrets() {
    }

    public static String getJWTSecret() {
        final String secret = System.getenv("MANDACARU_JWT_SECRET");

        if (secret == null) {
            throw new MissingSecuritySecretException("JWT secret not found");
        }

        return secret;
    }

    public static String getEnvironment() {
        final String environment = System.getenv("MANDACARU_ENVIRONMENT");

        if (environment == null) {
            throw new MissingSecuritySecretException("Environment not found");
        }

        return environment;
    }
}

package com.gabriel.rest.util;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class JWTUtils {

    public static boolean validateToken(String token) {
        try {
            Key hmacKey = getKey();

            Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(System.getenv("jwt_secret")),
                SignatureAlgorithm.HS256.getJcaName());
    }
}

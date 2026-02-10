package com.learncode.auth.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {


    private static final String SECRET_KEY = "YnllLWJ5ZS1lcnJvci10aGlzLWlzLWEtc2VjdXJlLWJhc2U2NC1rZXk";

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "satis@gmail.com");
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .claims(claims)
                .signWith(getKey())
                .compact();

    }

    private Key getKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY.getBytes()); //if secretKey is base64 use this
        //byte[] bytes1 = SECRET_KEY.getBytes(StandardCharsets.UTF_8); //if secretKey is plain text use this(stanadrd)

        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey)getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return getClaimsFromToken(token).getExpiration();
    }
}

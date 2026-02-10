package com.learncode.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

@Component
public class JWTUtils {

    private static final String SECRET_KEY = "YnllLWJ5ZS1lcnJvci10aGlzLWlzLWEtc2VjdXJlLWJhc2U2NC1rZXk";

    private Key getKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY.getBytes()); //if secretKey is base64 use this
        //byte[] bytes1 = SECRET_KEY.getBytes(StandardCharsets.UTF_8); //if secretKey is plain text use this(stanadrd)

        return Keys.hmacShaKeyFor(bytes);
    }
    public void validateToken(String token){
        Jwts.parser().verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token);

    }
}

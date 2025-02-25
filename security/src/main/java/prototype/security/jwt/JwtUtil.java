package prototype.security.jwt;



import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.secret_key}")
    private String secret_key;

    @Value("${security.access_token_expiration}")
    private String access_token_expiration;

    @Value("${security.refresh_token_expiration}")
    private String refresh_token_expiration;


    private final Key key = Keys.hmacShaKeyFor(secret_key.getBytes());

    // **Access Token 발급**
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // **Refresh Token 발급**
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refresh_token_expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // **JWT 검증 및 파싱**
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

package ir.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // کلید باید حداقل 32 بایت باشد برای الگوریتم HS256
    private final String SECRET = "my-super-secret-key-my-super-secret-key-my-super-secret-key";

    public String generateToken(String username , long expiration) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+expiration)).signWith(Keys.hmacShaKeyFor(SECRET.getBytes()),SignatureAlgorithm.HS256).compact();
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }
public boolean validateToken(String token) {

try {
    Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token);
    return true;
}
catch (Exception e) {return  false;}
}
}


    /*
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 ساعت

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
*/
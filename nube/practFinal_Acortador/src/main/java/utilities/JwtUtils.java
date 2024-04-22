package utilites;

import encapsulaciones.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    private static final String SECRET_KEY = "mysecretkey";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(Usuario user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setIssuer("PUCMM-ECT")
                .setSubject(String.valueOf(user.getId()))
//                .claim("admin", user.isAdmin())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
//            boolean isAdmin = (boolean) claims.get("admin");
//            return expiration.after(new Date()) && isAdmin;
            return expiration.after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}

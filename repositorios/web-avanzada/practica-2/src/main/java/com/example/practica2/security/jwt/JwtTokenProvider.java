package com.example.practica2.security.jwt;

import com.example.practica2.exception.UnauthorizedException;
import com.example.practica2.mock.Mock;
import com.example.practica2.utils.response.ApiResponse;
import com.example.practica2.utils.response.CustResponseBuilder;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value(value = "${app.jwtSecret}")
    private String jwtSecret;
    private CustResponseBuilder custResponseBuilder;

    public JwtTokenProvider(CustResponseBuilder custResponseBuilder) {
        this.custResponseBuilder = custResponseBuilder;
    }

    public String generateToken(Mock mock){
        Date createdAt = mock.getCreatedAt();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.SECOND, mock.getExpiration().getValue());
        Date expirationDate = calendar.getTime();

        return Jwts.builder()
                .setSubject(Long.toString(mock.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Long getMockIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());

    }

    public void validateToken(String authToken) {
        ResponseEntity<ApiResponse> response;

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
        } catch (SignatureException ex) {
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT signature");
            System.out.println("Invalid JWT signature");
            throw new UnauthorizedException(response);
        } catch (MalformedJwtException ex) {
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token");
            System.out.println("Invalid JWT signature");
            throw new UnauthorizedException(response);
        } catch (ExpiredJwtException ex) {
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "Expired JWT token");
            System.out.println("Expired JWT signature");
            throw new UnauthorizedException(response);
        } catch (UnsupportedJwtException ex) {
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "Unsupported JWT token");
            System.out.println("Unsupported JWT signature");
            throw new UnauthorizedException(response);
        } catch (IllegalArgumentException ex) {
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "JWT claims string is empty");
            System.out.println("JWT claims string is empty");
            throw new UnauthorizedException(response);
        }
//        return false;
    }
}

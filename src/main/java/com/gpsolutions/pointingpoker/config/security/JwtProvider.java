package com.gpsolutions.pointingpoker.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;

@Log
@Component
public class JwtProvider {

    public static final String TOKEN = "Token";
    private static final long EXPIRE_IN_DAYS = 30;

    @Value("$(app.jwt.secret)")
    private String jwtSecret;

    public void addTokenToCookies(final String email, final HttpServletResponse response) {
        final String JWT = Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(EXPIRE_IN_DAYS).toMillis()))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        final Cookie cookie = new Cookie(TOKEN, JWT);
        response.addCookie(cookie);
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (final ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (final UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (final MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (final SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (final Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public String getEmailFromToken(final String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

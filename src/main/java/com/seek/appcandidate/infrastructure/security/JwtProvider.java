package com.seek.appcandidate.infrastructure.security;

import com.seek.appcandidate.domain.enums.EErrorCode;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(UserDetails userDetails) {
        logger.info("Generating token for user: " + userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(secret))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            logger.info("Validating token: " + token);
            return isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            logger.error("Token expired: " + token);
            throw new CandidateException(EErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e){
            logger.error("Token unsupported: " + token);
            throw new CandidateException(EErrorCode.TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException | SecurityException | IllegalArgumentException e){
            logger.error("Token is not valid: " + token);
            throw new CandidateException(EErrorCode.TOKEN_IS_NOT_VALID);
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Key getKey(String s) {
        byte[] keyBytes = Decoders.BASE64.decode(s);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.seek.appcandidate.infrastructure.security;

import com.seek.appcandidate.domain.enums.EErrorCode;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class JwtAuthenticatorManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticatorManager.class);

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        logger.info("Authenticating token: " + authentication.getCredentials());
        return Mono.just(authentication)
                .map(authentication1 -> jwtProvider.validateToken(authentication.getCredentials().toString()))
                .onErrorResume(CandidateException.class, Mono::error)
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        Stream.of(claims.get("authorities"))
                                .map(role -> (List<Map<String, String>>) role)
                                .flatMap(role -> role.stream()
                                        .map(r -> r.get("authority"))
                                        .map(SimpleGrantedAuthority::new))
                                .collect(Collectors.toList())
                ));
    }
}

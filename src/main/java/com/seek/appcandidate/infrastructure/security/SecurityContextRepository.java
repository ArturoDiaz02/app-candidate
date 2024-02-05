package com.seek.appcandidate.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticatorManager jwtAuthenticatorManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("Init SecurityContextRepository");
        String token = exchange.getAttribute("token");
        return jwtAuthenticatorManager.authenticate(
                new UsernamePasswordAuthenticationToken(token, token)
        ).map(SecurityContextImpl::new);

    }
}

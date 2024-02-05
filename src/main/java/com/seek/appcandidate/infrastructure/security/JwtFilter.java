package com.seek.appcandidate.infrastructure.security;

import com.seek.appcandidate.domain.enums.EErrorCode;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class JwtFilter implements WebFilter {


    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        if (path.contains("/api/v1/auth") || path.contains("/swagger-doc")) return chain.filter(exchange);

        logger.info("Init JwtFilter");
        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || token.isEmpty()) return Mono.error(new CandidateException(EErrorCode.TOKEN_NOT_FOUND, token));

        if(!token.startsWith("Bearer ")) return Mono.error(new CandidateException(EErrorCode.AUTH_METHOD_NOT_SUPPORTED));

        exchange.getAttributes().put("token", token.replace("Bearer ", ""));

        return chain.filter(exchange);
    }
}

package com.example.gateway_service.jwt;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("✅ TOKEN: " +exchange.getRequest().getPath());

        ServerHttpRequest request = exchange.getRequest();
        if (request.getURI().getPath().contains("/auth/login")) {
            return chain.filter(exchange);
    }

        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (authHeaders == null || authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            String token = authHeaders.get(0).substring(7);
            Claims claims = jwtUtil.extractClaims(token);
            System.out.println("✅ Claims: " + claims);

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("username", claims.getSubject())
                    .header("role", claims.get("role", String.class))
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (Exception e) {
            System.out.println("❌ Token validation failed: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}


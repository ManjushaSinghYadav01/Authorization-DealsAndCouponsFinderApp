package com.ApiGateway.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import com.ApiGateway.util.RouteValidator;
import com.ApiGateway.Config.GatewayConfig;
import com.ApiGateway.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator. isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst( HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    return onError(exchange, "Invalid authorization header", HttpStatus.UNAUTHORIZED);
                }
                try {
                    Claims claim = jwtUtil.validateToken(authHeader);
                    if (claim == null) {
                        return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
                    }
                    String role = claim.get("role", String.class);
                    System.out.println("Role from token: " + role);
                    
                    System.out.println("Expected role from config: " + config.getRole());
                    if (config.getRole() != null && (role == null || !config.getRole().equalsIgnoreCase(role))) {
                        return onError(exchange, "Unauthorized access", HttpStatus.FORBIDDEN);
                    }
                } catch (Exception e) {
                    return onError(exchange, "Unauthorized access to application", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        DataBuffer buffer = response.bufferFactory().wrap(err.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
        private final String role;
        
        public Config(String role) {
        	if (role == null || role.isEmpty()) 
        	{
				throw new IllegalArgumentException("Role cannot be null or empty");
			}
        	
            this.role = role;
        }

        public String getRole() {
            return role;
        }

//        public void setRole(String role) { 
//            this.role = role;
//        }
    }
}

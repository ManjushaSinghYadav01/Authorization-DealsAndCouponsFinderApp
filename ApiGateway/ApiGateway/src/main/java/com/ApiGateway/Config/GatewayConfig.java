
package com.ApiGateway.Config;


import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.ApiGateway.Filter.AuthenticationFilter;

@Configuration
public class GatewayConfig {
	@Autowired
	AuthenticationFilter authenticationFilter;

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				
//				Auth Service
				.route("auth-service", r -> r.path("/auth/**")
						.uri("lb://AUTH-SERVICE"))
				
				.route("user-service-user", r -> r.path("/user/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("USER"))))
					    .uri("lb://USER-SERVICE"))
				
					.route("user-service-admin", r -> r.path("/admin/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ADMIN"))))
					    .uri("lb://USER-SERVICE"))
					
					.route("deal-service-admin", r -> r.path("/deals/admin/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ADMIN"))))
					    .uri("lb://DEAL-SERVICE"))
					
					.route("deal-service-user", r -> r.path("/deals/user/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("USER"))))
					    .uri("lb://DEAL-SERVICE"))
					
					.route("coupon-service-admin", r -> r.path("/coupons/admin/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ADMIN"))))
					    .uri("lb://COUPON-SERVICE"))
					
					.route("coupon-service-user", r -> r.path("/coupons/user/**")
					    .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("USER"))))
					    .uri("lb://COUPON-SERVICE"))
					
					.route("transaction-service-user", r -> r.path("/transactions/**")
							//.filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("USER"))))
							.uri("lb://TRANSACTION-SERVICE"))
					.build();
				
				
			
				

			
	}
}

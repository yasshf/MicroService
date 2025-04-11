package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route pour les tests
                .route(r -> r.path("/api/tests/**")
                        .uri("lb://pielearning"))  // Load Balancer via Eureka
                // Route pour les certificats
                .route(r -> r.path("/api/certificats/**")
                        .uri("lb://pielearning"))  // Load Balancer via Eureka
                .build();
    }
}

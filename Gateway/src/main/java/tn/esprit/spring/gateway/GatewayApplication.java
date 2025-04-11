package tn.esprit.spring.gateway;

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
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route to User-Management Service (port 8089)
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("lb://USERMANAGEMENT"))  // Direct port access

                // Route for API docs
                .route("api-docs", r -> r.path("/v3/api-docs/**")
                        .uri("http://localhost:8089"))
                .route("stat-service", r -> r.path("/api/statistics/**")
                        .uri("http://localhost:8089"))
                .build();
    }
}

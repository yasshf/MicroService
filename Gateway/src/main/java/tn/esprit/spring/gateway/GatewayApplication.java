package tn.esprit.spring.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;

import static org.springframework.web.servlet.function.RouterFunctions.route;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route to User-Management Service (port 8089)
                .route("categories-service", r -> r.path("/api/categories/**")
                        .uri("http://localhost:8081"))  // Direct port access
                .route("course-service", r -> r.path("/api/courses/**")
                        .uri("http://localhost:8081"))  // Direct port access
                .route("rating-service", r -> r.path("/api/rating/**")
                        .uri("http://localhost:8081"))  // Direct port access
                .route("images-service", r -> r.path("/api/images/**")
                        .uri("http://localhost:8081"))  // Direct port access
                .route("cache-service", r -> r.path("/api/cache/**")
                        .uri("http://localhost:8081"))  // Direct port access
                .build();
    }
}

package com.architekturausluginternetowych.carapigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class CarApiGatewayApplication {

    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(CarApiGatewayApplication.class, args);
    }

    @Autowired
    public CarApiGatewayApplication(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        ServiceInstance car = discoveryClient.getInstances("car-service").stream()
                .findFirst()
                .orElseThrow();

        ServiceInstance model = discoveryClient.getInstances("model-service").stream()
                .findFirst()
                .orElseThrow();

        return builder
                .routes()
                .route("models", r -> r
                        .host("localhost:8080")
                        .and()
                        .path("/api/models", "/api/models/**", "/api/cars/{id}/model")
                        .uri(model.getUri()))
                .route("cars", r -> r
                        .host("localhost:8080")
                        .and()
                        .path("/api/cars/{id}", "/api/cars")
                        .uri(car.getUri()))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}

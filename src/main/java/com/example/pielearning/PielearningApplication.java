package com.example.pielearning;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDiscoveryClient
@SpringBootApplication
public class PielearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(PielearningApplication.class, args);
    }

}

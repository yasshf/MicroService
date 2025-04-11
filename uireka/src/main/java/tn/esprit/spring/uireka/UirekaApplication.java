package tn.esprit.spring.uireka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class UirekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UirekaApplication.class, args);
    }

}

package tn.esprit.spring.erukaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ErukaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErukaServerApplication.class, args);
    }

}

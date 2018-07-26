package ru.vmsystems.glasscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlassApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlassApplication.class, args);
    }
}

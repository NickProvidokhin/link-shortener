package ru.providokhin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.providokhin.loggingstarter.LoggingStarterAutoConfiguration;

@SpringBootApplication
public class LinkShortenerApp {
    public static void main(String[] args) {
        LoggingStarterAutoConfiguration.println("It's working!!! Wow!");
        SpringApplication.run(LinkShortenerApp.class, args);
    }
}

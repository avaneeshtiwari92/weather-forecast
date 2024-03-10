package com.weather.WeatherForecast;

import com.weather.WeatherForecast.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfiguration.class})
public class Application {
    public static void main(String[] args) {
        System.out.println("Booting WeatherForecast Service, and all of it's glory...");
        SpringApplication.run(Application.class, args);
    }
}

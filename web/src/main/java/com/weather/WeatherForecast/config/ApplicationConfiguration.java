package com.weather.WeatherForecast.config;

import com.weather.WeatherForecast.services.config.ServicesConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan({"com.weather.WeatherForecast"})
@Import({
        ServicesConfiguration.class
})
public class ApplicationConfiguration {
}

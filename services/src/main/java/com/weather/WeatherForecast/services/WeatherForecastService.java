package com.weather.WeatherForecast.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.WeatherForecast.data.constants.MediaTypeConstants;
import com.weather.WeatherForecast.services.common.WeatherForecast;
import com.weather.WeatherForecast.services.utils.RedisUtility;
import com.weather.WeatherForecast.services.utils.WeatherForecastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class WeatherForecastService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${zip.code.base.api.key}")
    private String apiKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherForecastUtil weatherForecastUtil;

    @Autowired
    private RedisUtility redisUtility;

    public WeatherForecast getWeatherForecast(String zipCode) throws JsonProcessingException {
        // 1. Get from redis
        String forecastFromCache = redisUtility.getValue(zipCode);
        if(StringUtils.hasText(forecastFromCache)){
            WeatherForecast forecast = objectMapper.readValue(forecastFromCache, WeatherForecast.class);
            forecast.setFromCache(true);
            return forecast;
        }
        WeatherForecast weatherForecast = new WeatherForecast(zipCode);
        // 2. get lat long from external API by zip code.
        weatherForecast = weatherForecastUtil.getLatLong(weatherForecast);
        // 3. Get the Forecast from forecast API
        weatherForecast = weatherForecastUtil.getForecast(weatherForecast);
        redisUtility.setValue(zipCode, weatherForecast);
        return weatherForecast;
    }
}

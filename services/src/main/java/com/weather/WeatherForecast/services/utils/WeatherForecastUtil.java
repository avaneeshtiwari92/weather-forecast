package com.weather.WeatherForecast.services.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.WeatherForecast.services.common.WeatherForecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class WeatherForecastUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${zip.code.base.api.key}")
    private String zipCodeApiKey;

    @Value("${open.weather.api.key}")
    private String forecastApiKey;

    @Autowired
    private ObjectMapper objectMapper;

    public WeatherForecast getLatLong(WeatherForecast weatherForecast) throws JsonProcessingException {
        String zipCode = weatherForecast.getZipCode();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String res = restTemplate.exchange("https://app.zipcodebase.com/api/v1/search?apikey="+ zipCodeApiKey +"&codes="+zipCode, HttpMethod.GET,
                entity, String.class).getBody();

        if(StringUtils.hasText(res)){
            JsonNode root = objectMapper.readTree(res);

            JsonNode results = root.get("results");
            JsonNode postalCodeNode = results.get(zipCode);
            if (postalCodeNode.isArray()) {
                List<JsonNode> locations = objectMapper.convertValue(postalCodeNode, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));

                double latitude = locations.get(0).get("latitude").asDouble();
                double longitude = locations.get(0).get("longitude").asDouble();
                LOGGER.info(String.format("Lat {} and Long {} for the Zip code {}", latitude, longitude, zipCode));
                weatherForecast.setLatitude(latitude);
                weatherForecast.setLongitude(longitude);
            }
        }
        return weatherForecast;
    }

    public WeatherForecast getForecast(WeatherForecast weatherForecast) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String res = restTemplate.exchange("https://api.openweathermap.org/data/2.5/weather?lat="+weatherForecast.getLatitude()+
                        "&lon="+weatherForecast.getLongitude()+"&appid="+forecastApiKey+"&units=metric", HttpMethod.GET,
                entity, String.class).getBody();

        if(StringUtils.hasText(res)){
            JsonNode root = objectMapper.readTree(res);
            JsonNode results = root.get("main");
            weatherForecast.setCurrTemp(results.get("temp").asDouble());
            weatherForecast.setMaxTemp(results.get("temp_max").asDouble());
            weatherForecast.setMinTemp(results.get("temp_min").asDouble());
        }

//        LOGGER.info(res);
        return weatherForecast;
    }
}

package com.weather.WeatherForecast.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.WeatherForecast.data.constants.JsonKeyConstants;
import com.weather.WeatherForecast.data.constants.MediaTypeConstants;
import com.weather.WeatherForecast.services.WeatherForecastService;
import com.weather.WeatherForecast.services.common.WeatherForecast;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/weather_forecasts")
public class WeatherForecastEndpoint {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private WeatherForecastService weatherForecastService;

    @Operation(
            summary="Get Weather Forecast",
            operationId="readCollection")
    @Parameters({
            @Parameter(name = JsonKeyConstants.ZIP, description = "The Zip code for the .", in = ParameterIn.QUERY),
    })
    @RequestMapping(method = RequestMethod.GET, produces = MediaTypeConstants.APPLICATION_JSON)
    @ResponseStatus(value= HttpStatus.OK)
    public WeatherForecast read(@RequestParam(required = true) String zip) throws JsonProcessingException {
        LOGGER.info("The zip code is: "+zip);
        return weatherForecastService.getWeatherForecast(zip);
    }
}

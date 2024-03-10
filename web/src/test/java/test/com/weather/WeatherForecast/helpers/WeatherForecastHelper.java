package test.com.weather.WeatherForecast.helpers;

import com.weather.WeatherForecast.services.common.WeatherForecast;

public class WeatherForecastHelper {

    public static WeatherForecast generateWeatherData(String testZipCode){
        return new WeatherForecast(testZipCode);
    }
}

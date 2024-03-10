package test.com.weather.WeatherForecast.api.v1;

import com.weather.WeatherForecast.api.v1.WeatherForecastEndpoint;
import com.weather.WeatherForecast.data.constants.MediaTypeConstants;
import com.weather.WeatherForecast.services.WeatherForecastService;
import com.weather.WeatherForecast.services.common.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.com.weather.WeatherForecast.helpers.WeatherForecastHelper;
import org.eclipse.jetty.http.HttpStatus;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class WeatherForecastEndpointTests {

    private MockMvc mockMvc;
    @InjectMocks
    private WeatherForecastEndpoint mockWeatherForecastEndpoint;

    @Mock
    private WeatherForecastService mockWeatherForecastService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mockWeatherForecastEndpoint).build();
    }

    @Test
    public void readTest() throws Exception {
        String testZipCode = "200100";
        WeatherForecast weatherForecast = WeatherForecastHelper.generateWeatherData(testZipCode);
        Mockito.doReturn(weatherForecast).when(mockWeatherForecastService).getWeatherForecast(testZipCode);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather_forecasts?zip=" + testZipCode)
                        .contentType(MediaTypeConstants.APPLICATION_JSON)
                        .accept(MediaTypeConstants.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK_200))
                .andReturn();
    }
}

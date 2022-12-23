package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.model.Location;
import ru.vlpetko.weatherbot.model.WeatherData;
import ru.vlpetko.weatherbot.model.WeatherQuery;
import ru.vlpetko.weatherbot.repository.ClientRepository;
import ru.vlpetko.weatherbot.service.client.dto.DailyDto;
import ru.vlpetko.weatherbot.service.client.dto.ForecastDto;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenMeteoApiClient {

    @Value("${apiLineUrl}")
    String apiLineUrl;

    @Value("${request}")
    String request;

    private final RestTemplate restTemplate;

    private final ClientRepository clientRepository;

    @Transactional
    public List<WeatherData> getAndSaveForecast(double latitude, double longitude, String timeZone, Client client){
        List<WeatherData> weatherDataList = getForecastFromOpenSource(latitude, longitude, timeZone, client);
        return weatherDataList;
    }

    @Transactional
    public List<WeatherData> getForecastFromOpenSource(double latitude, double longitude, String timeZone, Client client){
        String coordinate = "latitude=" + latitude + "&longitude=" + longitude;
        ForecastDto resultJson;
        List<WeatherData> weatherDataList = new ArrayList<>();

        Location location = Location.builder().latitude(latitude).longitude(longitude).build();
        WeatherQuery weatherQuery = new WeatherQuery();
        weatherQuery.setDate(LocalDateTime.now());
        weatherQuery.setClient(client);
        weatherQuery.setLocation(location);
        location.setWeatherQuery(weatherQuery);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ForecastDto> responseEntity =
                restTemplate.exchange(apiLineUrl + coordinate + request + timeZone, HttpMethod.GET, entity,
                        ForecastDto.class);

        log.info("server status: " + responseEntity.getStatusCode());
        if (responseEntity.getStatusCode() == HttpStatus.valueOf(200)) {

            resultJson = (Objects.requireNonNull(responseEntity.getBody()));

            DailyDto dailyDto = resultJson.getDailyDto();
            for (int i = 0; i < dailyDto.getTemperatureMax().size(); i++) {
                WeatherData weatherData = new WeatherData();
                weatherData.setMaxTemp(dailyDto.getTemperatureMax().get(i));
                weatherData.setMinTemp(dailyDto.getTemperatureMin().get(i));
                weatherData.setWindSpeed(dailyDto.getWindSpeedMax().get(i));
                weatherData.setWindDirection(dailyDto.getWindDirectionDomin().get(i));
                weatherData.setCalendarDate(LocalDate.parse(dailyDto.getTime().get(i)));
                weatherDataList.add(weatherData);
            }
        weatherQuery.setWeatherDataList(weatherDataList);
        client.getWeatherQueries().add(weatherQuery);
        clientRepository.save(client);
        }
        return weatherDataList;
    }
}

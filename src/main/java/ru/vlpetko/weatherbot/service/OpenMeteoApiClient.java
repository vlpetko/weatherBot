package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vlpetko.weatherbot.mapper.CurrentWeatherUnitMapper;
import ru.vlpetko.weatherbot.model.*;
import ru.vlpetko.weatherbot.repository.ClientRepository;
import ru.vlpetko.weatherbot.repository.CurrentWeatherUnitRepository;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherUnitDto;
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

    private final CurrentWeatherUnitRepository currentWeatherUnitRepository;

    private final ClientRepository clientRepository;

    @Transactional
    public CurrentWeather getAndSaveData(){
        CurrentWeatherUnit currentWeatherUnit = getDataFromOpenSource("latitude=54.99&longitude=73.37",
                "&current_weather=true");
        currentWeatherUnitRepository.save(currentWeatherUnit);
        return currentWeatherUnit.getCurrentWeather();
    }

    @Transactional
    public CurrentWeather getAndSaveLocationData(String coordinate){
        CurrentWeatherUnit currentWeatherUnit = getDataFromOpenSource(coordinate, "&current_weather=true");
        currentWeatherUnitRepository.save(currentWeatherUnit);
        return currentWeatherUnit.getCurrentWeather();
    }

    @Transactional
    public List<WeatherData> getAndSaveForecast(double latitude, double longitude, String timeZone, Client client){
        List<WeatherData> weatherDataList = getForecastFromOpenSource(latitude, longitude, timeZone, client);
        return weatherDataList;
    }

    private CurrentWeatherUnit getDataFromOpenSource(String coordinate, String request){

        CurrentWeatherUnitDto resultJson;
        CurrentWeatherUnit result = new CurrentWeatherUnit();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CurrentWeatherUnitDto> responseEntity =
                restTemplate.exchange(apiLineUrl + coordinate + request, HttpMethod.GET, entity,
                        CurrentWeatherUnitDto.class);

        log.info("server status: " + responseEntity.getStatusCode());
        if (responseEntity.getStatusCode() == HttpStatus.valueOf(200)) {

            resultJson = (Objects.requireNonNull(responseEntity.getBody()));

            CurrentWeatherUnit unit = CurrentWeatherUnitMapper.INSTANCE.mapToCurrentWetherUnit(resultJson);
                CurrentWeather currentWeather = unit.getCurrentWeather();
                currentWeather.setCurrentWeatherUnit(unit);
                unit.setCurrentWeather(currentWeather);
                result = unit;
        }
        return result;
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

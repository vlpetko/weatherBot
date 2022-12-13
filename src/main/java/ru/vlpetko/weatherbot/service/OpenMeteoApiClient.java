package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vlpetko.weatherbot.mapper.CurrentWeatherUnitMapper;
import ru.vlpetko.weatherbot.model.CurrentWeather;
import ru.vlpetko.weatherbot.model.CurrentWeatherUnit;
import ru.vlpetko.weatherbot.repository.CurrentWeatherUnitRepository;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherUnitDto;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenMeteoApiClient {

    @Value("${apiLineUrl}")
    String apiLineUrl;

    private final RestTemplate restTemplate;

    private final CurrentWeatherUnitRepository currentWeatherUnitRepository;

    @Transactional
    public CurrentWeather getAndSaveData(){
        CurrentWeatherUnit currentWeatherUnit = getDataFromOpenSource();
        currentWeatherUnitRepository.save(currentWeatherUnit);
        return currentWeatherUnit.getCurrentWeather();
    }

    private CurrentWeatherUnit getDataFromOpenSource(){
        String coordinate = "latitude=54.99&longitude=73.37";
        String current = "&current_weather=true";
        CurrentWeatherUnitDto resultJson;
        CurrentWeatherUnit result = new CurrentWeatherUnit();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CurrentWeatherUnitDto> responseEntity =
                restTemplate.exchange(apiLineUrl + coordinate + current, HttpMethod.GET, entity,
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
}

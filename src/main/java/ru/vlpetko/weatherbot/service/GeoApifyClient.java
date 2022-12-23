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
import ru.vlpetko.weatherbot.service.client.dto.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoApifyClient {

    @Value("${api-geo-url}")
    String apiGeoUrl;

    @Value("${api-geo-key}")
    String apiGeoKey;

    private final RestTemplate restTemplate;

    public String getTimeZone(double latitude, double longitude){
            return getTimeZoneFromOpenSourse("lat=" + latitude + "&lon=" + longitude);
    }
    private String getTimeZoneFromOpenSourse(String coordinate){

        GeoApiDto resultsJson;
        String timeZone = new String();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GeoApiDto> responseEntity =
                restTemplate.exchange(apiGeoUrl + coordinate + apiGeoKey, HttpMethod.GET, entity,
                        GeoApiDto.class);
        log.info("server status: " + responseEntity.getStatusCode());

        if (responseEntity.getStatusCode() == HttpStatus.valueOf(200)) {

            resultsJson = (Objects.requireNonNull(responseEntity.getBody()));

            ResultsDto resultsDto = resultsJson.getResultsDto().get(0);
            TimeZoneDto timeZoneDto = resultsDto.getTimeZoneDto();
            timeZone = timeZoneDto.getName();
            log.info("timeZone is: {}", timeZone);
        }
        return timeZone;
    }


}

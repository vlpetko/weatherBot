package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoapifyClient {

    @Value("${api-geo-url}")
    String apiGeoUrl;

    private final RestTemplate restTemplate;

    public String getTimeZone(double latitude, double longitude){
            return getTimeZoneFromOpenSourse("lat=" + latitude + "&lon=" + longitude);
    }
    private String getTimeZoneFromOpenSourse(String coordinate){

    }


}

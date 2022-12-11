package ru.vlpetko.weatherbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.mapper.CurrentWeatherMapper;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/weather"})
public class WeatherConroller {

    private final OpenMeteoApiClient openMeteoApiClient;

    @GetMapping("/current")
    public CurrentReportDto getCurrentReportDto(){
        return CurrentWeatherMapper.INSTANCE.mapToCurrentReportDto(openMeteoApiClient.getAndSaveData());
    }
}

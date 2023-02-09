package ru.vlpetko.weatherbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.vlpetko.weatherbot.controller.dto.CityDto;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.controller.dto.WeatherRequestDto;
import ru.vlpetko.weatherbot.mapper.CityMapper;
import ru.vlpetko.weatherbot.mapper.CurrentWeatherMapper;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;
import ru.vlpetko.weatherbot.service.ScheduledService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/weather"})
public class WeatherConroller {

    private final OpenMeteoApiClient openMeteoApiClient;

    private final ScheduledService scheduledService;


    @PostMapping("/current")
    public CurrentReportDto getCurrentReportDto(
            @RequestBody WeatherRequestDto weatherRequestDto
    ) {
        log.info(weatherRequestDto.toString());
        return CurrentWeatherMapper.INSTANCE.mapToCurrentReportDto(openMeteoApiClient.getCurrentWeatherToController(
                weatherRequestDto));
    }

    @GetMapping("/city")
    public CityDto getCityDto() {

        CityDto cityDto = CityMapper.INSTANCE.mapToCityDto(scheduledService.getCity());
        return cityDto;
    }
}

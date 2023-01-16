package ru.vlpetko.weatherbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.mapper.CurrentWeatherMapper;
import ru.vlpetko.weatherbot.repository.ClientRepository;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/weather"})
public class WeatherConroller {

    private final OpenMeteoApiClient openMeteoApiClient;

    private final ClientRepository clientRepository;

    @GetMapping("/current")
    public CurrentReportDto getCurrentReportDto(){
        return CurrentWeatherMapper.INSTANCE.mapToCurrentReportDto(openMeteoApiClient.getCurrentWeatherToController(
                54.99, 73.37, "Asia/Omsk", clientRepository.getOptionalByUserId(1l).get()));
    }
}

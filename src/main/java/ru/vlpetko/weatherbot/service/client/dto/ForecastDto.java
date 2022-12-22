package ru.vlpetko.weatherbot.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ForecastDto {

    @JsonProperty("current_weather")
    private CurrentWeatherDto currentWeatherDto;

    @JsonProperty("daily")
    private DailyDto dailyDto;
}

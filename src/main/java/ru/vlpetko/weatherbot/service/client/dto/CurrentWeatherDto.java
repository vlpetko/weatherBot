package ru.vlpetko.weatherbot.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrentWeatherDto {

    @JsonProperty("temperature")
    private float temperature;

    @JsonProperty("windspeed")
    private float windspeed;

    @JsonProperty("winddirection")
    private float winddirection;

    @JsonProperty("time")
    private LocalDateTime localDateTime;

}

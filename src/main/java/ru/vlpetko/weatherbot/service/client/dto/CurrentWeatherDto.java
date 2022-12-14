package ru.vlpetko.weatherbot.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrentWeatherDto {

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("windspeed")
    private double windSpeed;

    @JsonProperty("winddirection")
    private double windDirection;

    @JsonProperty("time")
    private LocalDateTime requestDate;

}

package ru.vlpetko.weatherbot.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrentWeatherUnitDto {

    @JsonProperty("latitude")
    private float latitude;

    @JsonProperty("longitude")
    private float longitude;

    @JsonProperty("current_weather")
    private CurrentWeatherDto currentWeatherDto;

    @Override
    public String toString() {
        return "CurrentWeatherUnitDto{"
                + "latitude=" + latitude
                + ", longitude=" + longitude
                + ", currentWeatherDto=" + currentWeatherDto
                + '}';
    }
}

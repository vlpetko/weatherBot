package ru.vlpetko.weatherbot.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DailyDto {

    @JsonProperty("time")
    List<String> time;

    @JsonProperty("temperature_2m_max")
    List<Double> temperatureMax;

    @JsonProperty("temperature_2m_min")
    List<Double> temperatureMin;

    @JsonProperty("windspeed_10m_max")
    List<Double> windSpeedMax;

    @JsonProperty("winddirection_10m_dominant")
    List<Integer> windDirectionDomin;

}

package ru.vlpetko.weatherbot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequestDto implements Serializable {

    private double latitude;

    private double longitude;

    private String timeZone;

    private Long id;
}

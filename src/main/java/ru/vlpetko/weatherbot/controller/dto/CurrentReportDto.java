package ru.vlpetko.weatherbot.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrentReportDto {

    private Long currentId;

    private float temperature;

    private float windSpeed;

    private float windDirection;

    private LocalDateTime requestDate;
}

package ru.vlpetko.weatherbot.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrentReportDto {

    private Long currentId;

    private double temperature;

    private double windSpeed;

    private double windDirection;

    private LocalDateTime requestDate;
}

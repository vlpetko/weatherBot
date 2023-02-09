package ru.vlpetko.weatherbot.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CurrentReportDto {

    private Long currentId;

    private double currentTemp;

    private double windSpeed;

    private double windDirection;

    private LocalDate calendarDate;
}

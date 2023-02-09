package ru.vlpetko.weatherbot.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {

    private String name;

    private String alternateNames;

    private double latitude;

    private double longitude;

    private String timezone;
}

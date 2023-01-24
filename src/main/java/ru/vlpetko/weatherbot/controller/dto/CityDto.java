package ru.vlpetko.weatherbot.controller.dto;

import lombok.Data;

@Data
public class CityDto {

    private String name;

    private String alternateNames;

    private double latitude;

    private double longitude;

    private String timezone;
}

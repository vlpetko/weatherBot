package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.model.WeatherData;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface CurrentWeatherMapper {

    CurrentWeatherMapper INSTANCE = Mappers.getMapper(CurrentWeatherMapper.class);

    @Mapping(target = "currentTemp", source = "temperature")
    @Mapping(target = "calendarDate", source = "requestDate", qualifiedByName = "convertToLocalDate")
    WeatherData mapToWeatherData(CurrentWeatherDto currentWeatherDto);

    CurrentReportDto mapToCurrentReportDto(WeatherData weatherData);

    default LocalDate convertToLocalDate(LocalDateTime localDateTime){
        return localDateTime.toLocalDate();
    }
}

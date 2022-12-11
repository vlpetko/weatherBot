package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.model.CurrentWeather;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherDto;

@Mapper(uses = CurrentWeatherUnitMapper.class)
public interface CurrentWeatherMapper {

    CurrentWeatherMapper INSTANCE = Mappers.getMapper(CurrentWeatherMapper.class);

    CurrentWeather mapToCurrentWeather(CurrentWeatherDto currentWeatherDto);

    CurrentReportDto mapToCurrentReportDto(CurrentWeather currentWeather);
}

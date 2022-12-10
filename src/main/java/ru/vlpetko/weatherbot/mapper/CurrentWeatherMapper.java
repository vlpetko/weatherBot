package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CurrentWeatherUnitMapper.class)
public interface CurrentWeatherMapper {

    CurrentWeatherMapper INSTANCE = Mappers.getMapper(CurrentWeatherMapper.class);
}

package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.weatherbot.model.CurrentWeatherUnit;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherUnitDto;

@Mapper(uses = CurrentWeatherMapper.class)
public interface CurrentWeatherUnitMapper {

    CurrentWeatherUnitMapper INSTANCE = Mappers.getMapper(CurrentWeatherUnitMapper.class);

    CurrentWeatherUnit mapToCurrentWetherUnit(CurrentWeatherUnitDto currentWeatherUnitDto);
}

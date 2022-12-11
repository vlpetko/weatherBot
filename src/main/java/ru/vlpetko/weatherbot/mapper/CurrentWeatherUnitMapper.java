package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.weatherbot.model.CurrentWeatherUnit;
import ru.vlpetko.weatherbot.service.client.dto.CurrentWeatherUnitDto;

@Mapper(uses = CurrentWeatherMapper.class)
public interface CurrentWeatherUnitMapper {

    CurrentWeatherUnitMapper INSTANCE = Mappers.getMapper(CurrentWeatherUnitMapper.class);

//    @Mappings({
//            @Mapping(target = "currentWeather", source = "currentWeatherDto")
//    })
    CurrentWeatherUnit mapToCurrentWetherUnit(CurrentWeatherUnitDto currentWeatherUnitDto);
}

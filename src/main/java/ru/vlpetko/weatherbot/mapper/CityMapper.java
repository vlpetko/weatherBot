package ru.vlpetko.weatherbot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.weatherbot.controller.dto.CityDto;
import ru.vlpetko.weatherbot.model.City;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDto mapToCityDto(City city);
}

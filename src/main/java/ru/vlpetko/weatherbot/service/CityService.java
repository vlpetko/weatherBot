package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.City;
import ru.vlpetko.weatherbot.repository.CityRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getCity(String cityName) {
        if (Character.isUpperCase(cityName.charAt(0))) {
            return cityRepository.getByAlternateNamesContaining(cityName);
        } else {
            String cityToUpperCase = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
            return cityRepository.getByAlternateNamesContaining(cityToUpperCase);
        }
    }

    public City getCityById(Long id) {
        return cityRepository.getById(id);
    }
}

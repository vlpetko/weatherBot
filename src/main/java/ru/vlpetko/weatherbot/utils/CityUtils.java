package ru.vlpetko.weatherbot.utils;

import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.City;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityUtils {

    public static List<City> checkCityNames(List<City> cityList, String inputText) {
        List<City> checkedCities = new ArrayList<>();
        for (City city : cityList) {
            if (city.getName().equals(inputText)) {
                checkedCities.add(city);
            }
        }
        return checkedCities;
    }
}

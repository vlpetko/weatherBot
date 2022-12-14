package ru.vlpetko.weatherbot.utils;

import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.CurrentWeather;

@Service
public class WeatherUtils {

    public static String convertCurrentWeatherToString(CurrentWeather currentWeather){
        return "Текущая температура: " + currentWeather.getTemperature() + " Скорость ветра: "
                + currentWeather.getWindSpeed();
    }
}

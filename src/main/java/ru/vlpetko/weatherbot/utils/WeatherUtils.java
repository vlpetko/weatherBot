package ru.vlpetko.weatherbot.utils;

import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.WeatherData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherUtils {

    public static String convertForecastToString(List<WeatherData> weatherDataList){
        
        StringBuilder stringBuilder = new StringBuilder();

        for (WeatherData weatherData : weatherDataList) {
            String weather = weatherData.getCalendarDate() + " - \uD83C\uDF21 Температура от " + weatherData.getMinTemp() +
                    " до " + weatherData.getMaxTemp() + " градусов, \uD83C\uDF2C ветер " + getWindDirection(weatherData.getWindDirection()) +
                    ", до " + weatherData.getWindSpeed() + " м/с;\n";
            stringBuilder.append(weather);
        }
        
        return "Прогноз погоды на:\n\n" + stringBuilder.toString();
    }

    private static String getWindDirection(double windDirection){

        if (windDirection < 67.5 && windDirection > 22.5){
            return "Северо-восточный";
        } else if (windDirection < 112.0 && windDirection > 67.5){
            return "Восточный";
        } else if (windDirection < 157.5 && windDirection > 112.5){
            return "Юго-восточный";
        } else if (windDirection < 202.5 && windDirection > 157.5){
            return "Южный";
        } else if (windDirection < 247.5 && windDirection > 202.5){
            return "Юго-западный";
        } else if (windDirection < 292.5 && windDirection > 247.5){
            return "Западный";
        } else if (windDirection < 337.5 && windDirection > 292.5){
            return "Северо-западный";
        } else {
            return "Северный";
        }

    }
}

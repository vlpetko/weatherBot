package ru.vlpetko.weatherbot.utils;

import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.WeatherData;

import java.util.List;

@Service
public class WeatherUtils {

    public static String convertForecastToString(List<WeatherData> weatherDataList){
        
        StringBuilder stringBuilder = new StringBuilder();

        for (WeatherData weatherData : weatherDataList) {
            String weather = weatherData.getCalendarDate() + " - Температура от " + weatherData.getMinTemp() +
                    " до " + weatherData.getMaxTemp() + ", ветер до " + weatherData.getWindSpeed() +
                    "м/с, направление - " + weatherData.getWindDirection() + ";  ";
            stringBuilder.append(weather);
        }
        
        return "Прогноз погоды на: " + stringBuilder.toString();
    }
}

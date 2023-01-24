package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.City;
import ru.vlpetko.weatherbot.model.WeatherQuery;
import ru.vlpetko.weatherbot.repository.CityRepository;
import ru.vlpetko.weatherbot.repository.WeatherQueryRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final RestartService restartService;

    private final WeatherQueryRepository weatherQueryRepository;

    private final CityRepository cityRepository;

    @Transactional
    @Scheduled(cron = "0 */10 * * * *")
    public void getTimeEndRequests(){

        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(10l);

        List<WeatherQuery> weatherQueryList =
                weatherQueryRepository.getWeatherQueriesByQueryStatusEquals("processing");

        for (WeatherQuery weatherQuery: weatherQueryList) {
            if (weatherQuery.getDate().isBefore(ChronoLocalDateTime.from(localDateTime))){
                String chatId = weatherQuery.getClient().getUserId().toString();
                restartService.setRestartKeyBoard(chatId);
                weatherQuery.setQueryStatus("completed");
                weatherQueryRepository.save(weatherQuery);
            }
        }
    }

    public City getCity(){
        City city = cityRepository.getByAlternateNamesContaining("Омск").get(0);
        System.out.println(city);
        return city;
    }
}

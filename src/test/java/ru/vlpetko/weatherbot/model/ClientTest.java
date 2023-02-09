package ru.vlpetko.weatherbot.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Client")
class ClientTest {

    @Test
    void haveCorrectConstructorWithAllArguments(){
        Long id = 1L;
        WeatherQuery testWeatherQuery = WeatherQuery.builder()
                .id(id)
                .date(LocalDateTime.of(2023, 01, 30, 19, 18, 00))
                .queryStatus("complite")
                .client(new Client())
                .weatherDataList(new ArrayList<>())
                .build();
        List<WeatherQuery> weatherDataList = new ArrayList<>();
        weatherDataList.add(testWeatherQuery);

        Client testClient = Client.builder()
                .id(id)
                .userId(1511095653L)
                .firstName("firstName")
                .lastName("lastName")
                .registrateDate(LocalDateTime.of(2023, 01, 30, 19, 18, 00))
                .weatherQueries(weatherDataList)
                .build();
        assertEquals(1511095653L, testClient.getUserId());
        assertEquals("complite", testClient.getWeatherQueries().get(0).getQueryStatus());

    }
}
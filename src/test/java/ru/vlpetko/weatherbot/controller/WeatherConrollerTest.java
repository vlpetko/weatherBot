package ru.vlpetko.weatherbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vlpetko.weatherbot.controller.dto.CurrentReportDto;
import ru.vlpetko.weatherbot.controller.dto.WeatherRequestDto;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.model.WeatherData;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;
import ru.vlpetko.weatherbot.service.ScheduledService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс WeatherConroller")
@WebMvcTest(WeatherConroller.class)
class WeatherConrollerTest {

    @MockBean
    private OpenMeteoApiClient openMeteoApiClient;

    @MockBean
    private ScheduledService scheduledService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private WeatherConroller weatherConroller;

    @BeforeEach
    void setUp() {
        weatherConroller = new WeatherConroller(openMeteoApiClient, scheduledService);
    }

    @Test
    void getCurrentReportDto() throws Exception {
        WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                .latitude(37.3)
                .longitude(73.7)
                .timeZone("Asia/Omsk")
                .id(1L)
                .build();
        CurrentReportDto currentReportDto = CurrentReportDto.builder()
                .currentId(1L)
                .currentTemp(24.0)
                .windSpeed(11.2)
                .windDirection(124.0)
                .calendarDate(LocalDate.of(2023, 02, 02))
                .build();
        Client testClient = Client.builder()
                .id(1L)
                .userId(1511095653L)
                .firstName("firstName")
                .lastName("lastName")
                .registrateDate(LocalDateTime.of(2023, 01, 30, 19, 18, 00))
                .weatherQueries(new ArrayList<>())
                .build();
        WeatherData weatherData = WeatherData.builder()
                .id(1L)
                .currentTemp(24.0)
                .windSpeed(11.2)
                .windDirection(124.0)
                .calendarDate(LocalDate.of(2023, 02, 02))
                .build();

        var expectedBody = mapper.writeValueAsString(weatherRequestDto);
        var expectedResult = mapper.writeValueAsString(currentReportDto);
        var expextedData = mapper.writeValueAsString(weatherData);

        given(openMeteoApiClient.getCurrentWeatherToController(weatherRequestDto))
                .willReturn(weatherData);

        mockMvc.perform(post("/api/weather/current").contentType(APPLICATION_JSON)
                .content(expectedBody))
                .andExpect(status().isOk());
//                .andExpect(content().json(expextedData));

    }
}
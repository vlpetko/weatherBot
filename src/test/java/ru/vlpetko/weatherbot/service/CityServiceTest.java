package ru.vlpetko.weatherbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vlpetko.weatherbot.model.City;
import ru.vlpetko.weatherbot.repository.CityRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс CityService")
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityService(cityRepository);
    }

    @Test
    void getCity() {
        City city = City.builder()
                .id(1L)
                .name("Omsk")
                .asciiName("Omsk")
                .alternateNames("Омск")
                .latitude(53.0)
                .longitude(75.0)
                .population(666777L)
                .timezone("Asia/Dubai")
                .modificationDate(LocalDate.of(2020, 12, 31))
                .build();
        given(cityRepository.getById(1L)).willReturn(city);
        assertThat(cityService.getCityById(1L)).isNotNull();
    }
}
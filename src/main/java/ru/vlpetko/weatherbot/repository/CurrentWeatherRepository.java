package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.CurrentWeather;

@Repository
public interface CurrentWeatherRepository extends JpaRepository<CurrentWeather,Long> {
}

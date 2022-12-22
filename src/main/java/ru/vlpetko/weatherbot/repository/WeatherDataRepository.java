package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.WeatherData;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
}

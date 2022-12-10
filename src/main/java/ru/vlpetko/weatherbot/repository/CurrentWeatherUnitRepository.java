package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.CurrentWeatherUnit;

@Repository
public interface CurrentWeatherUnitRepository extends JpaRepository<CurrentWeatherUnit,Long> {
}

package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.WeatherQuery;

@Repository
public interface WeatherQueryRepository extends JpaRepository<WeatherQuery,Long> {
}

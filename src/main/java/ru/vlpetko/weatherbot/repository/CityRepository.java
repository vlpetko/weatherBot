package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    @Query("SELECT c FROM City c WHERE c.alternateNames LIKE %:alternateNames%")
    List<City> getByAlternateNamesContaining(@Param("alternateNames") String alternateNames);
}

package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
}

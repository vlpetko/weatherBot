package ru.vlpetko.weatherbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vlpetko.weatherbot.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("SELECT c FROM Client c WHERE c.userId = : userId")
    Optional<Client> getOptionalByUserId(@Param("userId") Long userId);

    Client getClientByUserId(Long userId);
}

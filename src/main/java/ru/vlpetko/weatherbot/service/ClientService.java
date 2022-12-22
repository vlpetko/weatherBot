package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.repository.ClientRepository;

import java.util.ArrayList;
import java.util.Optional;

import static ru.vlpetko.weatherbot.utils.TimeUtils.convertLongToLocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

   private final ClientRepository clientRepository;

    public Client chekClient(Long userId, String firstName, String lastName, long registrateDate){
        Optional<Client> clientOptional = Optional.ofNullable(clientRepository.getClientByUserId(userId));
        if(clientOptional.isEmpty()){
            Client client = new Client();
            client.setUserId(userId);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setRegistrateDate(convertLongToLocalDateTime(registrateDate));
            client.setWeatherQueries(new ArrayList<>());
            clientRepository.save(client);
            return client;
        } else {
            return clientOptional.get();
        }
    }
}
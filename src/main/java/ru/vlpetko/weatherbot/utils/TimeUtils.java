package ru.vlpetko.weatherbot.utils;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimeUtils {
    public static LocalDateTime convertLongToLocalDateTime(long registrateDate){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(registrateDate), ZoneId.systemDefault());
    }
}

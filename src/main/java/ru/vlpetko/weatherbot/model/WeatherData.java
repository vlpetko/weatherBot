package ru.vlpetko.weatherbot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double currentTemp;

    private double maxTemp;

    private double minTemp;

    private double windSpeed;

    private double windDirection;

    private LocalDate calendarDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private WeatherQuery weatherQuery;

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", currentTemp=" + currentTemp +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", requestDate=" + calendarDate +
                ", weatherQuery=" + weatherQuery +
                '}';
    }
}

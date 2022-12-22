package ru.vlpetko.weatherbot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "current_weather")
public class CurrentWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currentId;

    private double temperature;

    private double windSpeed;

    private double windDirection;

    private LocalDateTime requestDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private CurrentWeatherUnit currentWeatherUnit;

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "currentId=" + currentId +
                ", temperature=" + temperature +
                ", windspeed=" + windSpeed +
                ", winddirection=" + windDirection +
                ", localDateTime=" + requestDate +
                ", currentWeatherUnit=" + currentWeatherUnit
                + '}';
    }
}

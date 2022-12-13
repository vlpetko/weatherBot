package ru.vlpetko.weatherbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "current_weather")
public class CurrentWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currentId;

    private float temperature;

    private float windSpeed;

    private float windDirection;

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

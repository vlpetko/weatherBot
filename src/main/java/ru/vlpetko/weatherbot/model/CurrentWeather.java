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
@Table(name = "currentweather")
public class CurrentWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currentId;

    private float temperature;

    private float windspeed;

    private float winddirection;

    private LocalDateTime reqwestDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private CurrentWeatherUnit currentWeatherUnit;

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "currentId=" + currentId +
                ", temperature=" + temperature +
                ", windspeed=" + windspeed +
                ", winddirection=" + winddirection +
                ", localDateTime=" + reqwestDate +
                ", currentWeatherUnit=" + currentWeatherUnit
                + '}';
    }
}

package ru.vlpetko.weatherbot.model;

import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "weather_units")
public class CurrentWeatherUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    private double latitude;

    private double longitude;

    @OneToOne(mappedBy = "currentWeatherUnit", cascade = CascadeType.ALL)
    private CurrentWeather currentWeather;

    @Override
    public String toString() {
        return "CurrentWeatherUnit{"
                + "unitId=" + unitId
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + ", currentWeather=" + currentWeather
                + '}';
    }
}

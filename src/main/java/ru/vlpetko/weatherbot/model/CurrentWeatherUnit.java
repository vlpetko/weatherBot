package ru.vlpetko.weatherbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weatherunit")
public class CurrentWeatherUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    private float latitude;

    private float longitude;

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

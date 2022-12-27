package ru.vlpetko.weatherbot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "weather_query")
public class WeatherQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String queryStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(mappedBy = "weatherQuery", cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(mappedBy = "weatherQuery", cascade = CascadeType.ALL)
    private List <WeatherData> weatherDataList;

}

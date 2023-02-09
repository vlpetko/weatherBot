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
@Builder
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String firstName;

    private String lastName;

    private LocalDateTime registrateDate;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<WeatherQuery> weatherQueries;

}

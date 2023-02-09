package ru.vlpetko.weatherbot.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String asciiName;

    private String alternateNames;

    private double latitude;

    private double longitude;

    private char featureClass;

    private String featureCode;

    private String countryCode;

    private String cc2;

    @Column(name = "admin1_code")
    private String admin1Code;

    @Column(name = "admin2_code")
    private String admin2Code;

    @Column(name = "admin3_code")
    private String admin3Code;

    @Column(name = "admin4_code")
    private String admin4Code;

    private Long population;

    private String elevation;

    private int dem;

    private String timezone;

    private LocalDate modificationDate;

}

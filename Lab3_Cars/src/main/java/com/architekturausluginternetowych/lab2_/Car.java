package com.architekturausluginternetowych.lab2_;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars", schema = "CAR_APP")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String vin;

    @Column(name = "color")
    public String color;

    @ManyToOne
    @JoinColumn(name = "model")
    public Model model;

    @OneToOne
    @JoinColumn(name = "carImage")
    public CarImage carImage;
}

package com.architekturausluginternetowych.lab2_;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carsImages")
public class CarImage {

    @Id
    public String vin;

    public String author;

    public String description;

}

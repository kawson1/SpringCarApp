package com.architekturausluginternetowych.lab2_;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "models", schema = "CAR_APP")
public class Model {

    @Id
    @Column(unique = true)
    public String name;

    @Column(name = "horse_power")
    public int hp;

}

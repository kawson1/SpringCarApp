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
@Table(name = "models")
public class Model {

    @Id
    @Column(unique = true)
    public String name;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    public List<Car> cars;

}

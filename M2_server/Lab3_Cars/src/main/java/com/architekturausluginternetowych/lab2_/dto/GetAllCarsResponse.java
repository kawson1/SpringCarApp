package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Car;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponse {

    @Singular("car")
    List<String> cars;

    public static Function<Collection<Car>, GetAllCarsResponse> entityToDtoMapper(){
        return cars -> {
            GetAllCarsResponseBuilder response = GetAllCarsResponse.builder();
            cars.stream()
                    .map(Car::getVin)
                    .forEach(response::car);
            return response.build();
        };
    }
}

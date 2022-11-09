package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
@Builder
public class GetCarResponse {

    private String VIN;

    private String color;

    private String model;

    public static Function<Car, GetCarResponse> entityToDtoMapper(){
        return car -> GetCarResponse.builder()
                .VIN(car.getVin())
                .color(car.getColor())
                .model(car.getModel().getName())
                .build();
    }
}

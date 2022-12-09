package com.architekturausluginternetowych.lab2_.dto;

import java.util.function.BiFunction;

import com.architekturausluginternetowych.lab2_.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarRequest {

    private String color;

    public static BiFunction<Car, UpdateCarRequest, Car> dtoToEntityUpdater(){
        return (car, request) -> {
            car.setColor(request.getColor());
            return car;
        };
    }

}

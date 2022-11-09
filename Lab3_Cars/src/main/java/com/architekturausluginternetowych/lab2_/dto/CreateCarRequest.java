package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Setter
@Getter
public class CreateCarRequest {

    private String VIN;

    private String color;

    /**
     * Name of the car's model
     */
    private String model;

    /**
     * @param modelFunction - function which convert model name (String) into model entity (Model)
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateCarRequest, Car> dtoToEntityMapper(Function<String, Model> modelFunction){
        return request -> Car.builder()
                .vin(request.getVIN())
                .color(request.getColor())
                .model(modelFunction.apply(request.getModel()))
                .build();
    }

}

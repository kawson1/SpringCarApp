package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Setter
@Getter
public class CreateModelRequest {

    private String name;

    public static Function<CreateModelRequest, Model> dtoToEntityMapper(){
        return request -> Model.builder()
                .name(request.getName())
                .build();
    }

}

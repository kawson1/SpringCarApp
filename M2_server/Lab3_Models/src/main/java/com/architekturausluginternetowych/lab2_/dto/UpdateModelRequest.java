package com.architekturausluginternetowych.lab2_.dto;

import java.util.function.BiFunction;

import com.architekturausluginternetowych.lab2_.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateModelRequest {

    private int hp;

    public static BiFunction<Model, UpdateModelRequest, Model> dtoToEntityUpdater(){
        return (model, request) -> {
            model.setHp(request.getHp());
            return model;
        };
    }

}

package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
@Builder
public class GetModelResponse {

    private String name;

    private int hp;

    public static Function<Model, GetModelResponse> entityToDtoMapper(){
        return model -> GetModelResponse.builder()
                .name(model.getName())
                .hp(model.getHp())
                .build();
    }
}

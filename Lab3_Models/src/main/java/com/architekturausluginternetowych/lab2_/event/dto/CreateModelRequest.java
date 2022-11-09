package com.architekturausluginternetowych.lab2_.event.dto;

import com.architekturausluginternetowych.lab2_.Model;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateModelRequest {

    private String model;

    public static Function<Model, CreateModelRequest> entityToDtoMapper(){
        return entity -> CreateModelRequest.builder()
                .model(entity.getName())
                .build();
    }
}

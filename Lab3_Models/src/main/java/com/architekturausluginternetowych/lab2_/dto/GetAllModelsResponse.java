package com.architekturausluginternetowych.lab2_.dto;

import com.architekturausluginternetowych.lab2_.Model;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllModelsResponse {

    @Singular("model")
    List<String> models;

    public static Function<Collection<Model>, GetAllModelsResponse> entityToDtoMapper(){
        return models -> {
            GetAllModelsResponseBuilder response = GetAllModelsResponse.builder();
            models.stream()
                    .map(Model::getName)
                    .forEach(response::model);
            return response.build();
        };
    }
}

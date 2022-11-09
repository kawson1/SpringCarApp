package com.architekturausluginternetowych.lab2_.event.repository;

import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.event.dto.CreateModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ModelEventRepository {
    private RestTemplate restTemplate;

    @Autowired
    public ModelEventRepository(@Value("${cars.url}") String baseUrl){
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public void delete(Model model){
        restTemplate.delete("/models/{modelname}", model.getName());
    }

    public void create(Model model){
        restTemplate.postForLocation("/models", CreateModelRequest.entityToDtoMapper().apply(model));
    }

}

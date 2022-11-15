package com.architekturausluginternetowych.lab2_.Controllers;

import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Services.ModelService;
import com.architekturausluginternetowych.lab2_.dto.CreateModelRequest;
import com.architekturausluginternetowych.lab2_.dto.GetAllModelsResponse;
import com.architekturausluginternetowych.lab2_.dto.GetModelResponse;
import com.architekturausluginternetowych.lab2_.dto.UpdateModelRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.coyote.Response;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/models")
public class ModelController {

    @Autowired
    public ModelService ModelService;

    @Autowired
    public ModelController(ModelService ModelService){
        this.ModelService = ModelService;
    }

    @PostMapping("")
    public ResponseEntity<Model> create(@RequestBody CreateModelRequest request, UriComponentsBuilder builder){
        Model Model = CreateModelRequest.dtoToEntityMapper().apply(request);
        ModelService.addModel(Model);
        return ResponseEntity.created(builder.pathSegment("api", "models", "{name}")
                .buildAndExpand(Model.getName()).toUri()).build();
        // return ResponseEntity.ok(Model);
    }

    @GetMapping("{name}")
    public ResponseEntity<GetModelResponse> findModelById(@PathVariable String name){
        return ModelService.findModelById(name)
                .map(Model -> ResponseEntity.ok(GetModelResponse.entityToDtoMapper().apply(Model)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<GetAllModelsResponse> findAllModels(){
        return ResponseEntity.ok(GetAllModelsResponse.entityToDtoMapper().apply(ModelService.findAllModels()));
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteModel(@PathVariable String name){
        Optional<Model> Model = ModelService.findModelById(name);
        if(Model.isPresent()){
            ModelService.deleteModel(Model.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{name}")
    public ResponseEntity<Void> updateModel(@RequestBody UpdateModelRequest request, @PathVariable String name){
        Optional<Model> model = ModelService.findModelById(name);
        if(model.isPresent()){
            UpdateModelRequest.dtoToEntityUpdater().apply(model.get(), request);
            ModelService.updateModel(model.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    // public ResponseEntity<Void> addModel()

}

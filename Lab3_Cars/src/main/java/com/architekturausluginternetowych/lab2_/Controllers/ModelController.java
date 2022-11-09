package com.architekturausluginternetowych.lab2_.Controllers;

import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Services.ModelService;
import com.architekturausluginternetowych.lab2_.dto.CreateModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@RestController
@RequestMapping("api/models")
public class ModelController {

    public ModelService ModelService;

    @Autowired
    public ModelController(ModelService ModelService){
        this.ModelService = ModelService;
    }

    @PostMapping
    public ResponseEntity<Model> create(@RequestBody CreateModelRequest request, UriComponentsBuilder builder){
        Model Model = CreateModelRequest.dtoToEntityMapper().apply(request);
        ModelService.addModel(Model);
        return ResponseEntity
                .created(builder
                        .pathSegment("api", "models", "{name}")
                        .buildAndExpand(Model.getName()).toUri())
                .build();
        // return ResponseEntity.ok(Model);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteModel(@PathVariable String name){
        Optional<Model> model = ModelService.find(name);
        if(model.isPresent()){
            ModelService.deleteModel(model.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

}

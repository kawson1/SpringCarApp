package com.architekturausluginternetowych.lab2_.Services;

import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    ModelRepository ModelRepository;

    public ModelService(ModelRepository ModelRepository) {
        this.ModelRepository = ModelRepository;
    }

    public void addModel(Model Model){ ModelRepository.save(Model); }

    public void deleteModel(Model Model){ ModelRepository.delete(Model); }

    public Optional<Model> find(String name) { return ModelRepository.findById(name); }

    // public List<Model> findAllModels(){ return ModelRepository.findAll(); }

    // public void updateModel(Model Model) { ModelRepository.save(Model); }
}

package com.architekturausluginternetowych.lab2_.Services;

import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Repositories.ModelRepository;
import com.architekturausluginternetowych.lab2_.event.repository.ModelEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {

    ModelRepository ModelRepository;

    ModelEventRepository modelEventRepository;

    @Autowired
    public ModelService(ModelRepository ModelRepository, ModelEventRepository modelEventRepository) {
        this.ModelRepository = ModelRepository;
        this.modelEventRepository = modelEventRepository;
    }

    @Transactional
    public void addModel(Model Model){
        ModelRepository.save(Model);
        modelEventRepository.create(Model);
    }

    @Transactional
    public void deleteModel(Model Model){
        ModelRepository.delete(Model);
        modelEventRepository.delete(Model);
    }

    public Optional<Model> findModelById(String name) { return ModelRepository.findById(name); }

    public List<Model> findAllModels(){ return ModelRepository.findAll(); }

    public void updateModel(Model Model) {
        ModelRepository.save(Model);
    }
}

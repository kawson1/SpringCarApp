package com.architekturausluginternetowych.lab2_.Repositories;

import com.architekturausluginternetowych.lab2_.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, String> {

    @Override
    Optional<Model> findById(String name);

    @Override
    List<Model> findAll();

    @Override
    void deleteById(String name);

}

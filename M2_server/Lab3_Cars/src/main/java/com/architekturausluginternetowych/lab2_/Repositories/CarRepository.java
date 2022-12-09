package com.architekturausluginternetowych.lab2_.Repositories;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Override
    Optional<Car> findById(Long id);

    Optional<Car> findByVin(String vin);

    Optional<Car> findByModelAndId(Model model, Long id);

    @Override
    List<Car> findAll();

    List<Car> findAllByModel(Model model);

    @Override
    void deleteById(Long id);

}

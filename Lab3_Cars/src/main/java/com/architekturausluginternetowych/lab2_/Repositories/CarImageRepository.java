package com.architekturausluginternetowych.lab2_.Repositories;

import com.architekturausluginternetowych.lab2_.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, String> {

}

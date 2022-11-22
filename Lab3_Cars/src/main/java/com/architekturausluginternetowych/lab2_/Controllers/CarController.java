package com.architekturausluginternetowych.lab2_.Controllers;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.Services.CarService;
import com.architekturausluginternetowych.lab2_.Services.ModelService;
import com.architekturausluginternetowych.lab2_.dto.CreateCarRequest;
import com.architekturausluginternetowych.lab2_.dto.GetAllCarsResponse;
import com.architekturausluginternetowych.lab2_.dto.GetCarResponse;
import com.architekturausluginternetowych.lab2_.dto.UpdateCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@RestController
@RequestMapping("api/cars")
public class CarController {

    public CarService carService;

    public ModelService modelService;

    @Autowired
    public CarController(CarService carService, ModelService modelService){
        this.carService = carService;
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody CreateCarRequest request, UriComponentsBuilder builder){
        Car car = CreateCarRequest
                .dtoToEntityMapper(modelName -> modelService.find(modelName).orElseThrow())
                .apply(request);
        // carService.addCar(car);
        car = carService.addCar(car);
        return ResponseEntity.created(builder.pathSegment("api", "cars", "{vin}")
                .buildAndExpand(car.getVin()).toUri()).build();
        // return ResponseEntity.ok(car);
    }

    @GetMapping("{vin}")
    public ResponseEntity<GetCarResponse> findCarByVin(@PathVariable String vin){
        return carService.findCarByVIN(vin)
                .map(car -> ResponseEntity.ok(GetCarResponse.entityToDtoMapper().apply(car)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<GetAllCarsResponse> findAllCars(){
        return ResponseEntity.ok(GetAllCarsResponse.entityToDtoMapper().apply(carService.findAll()));
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
//        Optional<Car> car = carService.findCarById(id);
//        if(car.isPresent()){
//            carService.deleteCar(car.get());
//            return ResponseEntity.accepted().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("{vin}")
    public ResponseEntity<Void> deleteCar(@PathVariable String vin){
        Optional<Car> car = carService.findCarByVIN(vin);
        if(car.isPresent()){
            carService.deleteCar(car.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{vin}")
    public ResponseEntity<Void> updateCar(@RequestBody UpdateCarRequest request, @PathVariable String vin){
        Optional<Car> car = carService.findCarByVIN(vin);
        if(car.isPresent()){
            UpdateCarRequest.dtoToEntityUpdater().apply(car.get(), request);
            carService.updateCar(car.get());
            return ResponseEntity.accepted().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

}

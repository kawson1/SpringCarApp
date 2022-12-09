package com.architekturausluginternetowych.lab2_.Controllers;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Services.CarService;
import com.architekturausluginternetowych.lab2_.Services.ModelService;
import com.architekturausluginternetowych.lab2_.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/models/{modelname}/cars")
public class CarModelController {

    private CarService carService;

    private ModelService modelService;

    @Autowired
    public CarModelController(CarService carService, ModelService modelService){
        this.carService = carService;
        this.modelService = modelService;
    }

    /**
     * Find all cars of specific model name
     * @param modelname Model's name
     * @return list of cars with specific model name
     */
    @GetMapping
    public ResponseEntity<GetAllCarsResponse> getCars(@PathVariable("modelname") String modelname){
        Optional<Model> model = modelService.find(modelname);
        return model.map(model_ -> ResponseEntity.ok(GetAllCarsResponse.entityToDtoMapper().apply(carService.findAll(model_))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Find car with specific model name and car ID
     * @param modelname Model's name
     * @param id        car ID
     * @return car with specific model name and ID
     */
    @GetMapping("{id}")
    public ResponseEntity<GetCarResponse> getCars(@PathVariable("modelname") String modelname,
                                                  @PathVariable("id") Long id){
        Optional<Car> car = carService.find(modelname, id);
        return car.map(car_ -> ResponseEntity.ok(GetCarResponse.entityToDtoMapper().apply(car_)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@PathVariable("modelname") String modelname,
                                              @RequestBody CreateCarRequest request,
                                              UriComponentsBuilder builder){
        Optional<Model> model = modelService.find(modelname);
        if (model.isPresent()){
            Car car = CreateCarRequest
                    .dtoToEntityMapper(value -> modelService.find(value).orElseThrow()) // or model::get ???
                    .apply(request);
            car = carService.addCar(car);
            return ResponseEntity.created(builder.pathSegment("api", "models", "{modelname}", "cars", "{id}")
                    .buildAndExpand(model.get().getName(), car.getId()).toUri()).build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Delete selected car (specific by ID)
     * @param modelname Model name
     * @param id        Car ID
     * @return  accepted if model were found, not found if weren't.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("modelname") String modelname,
                                          @PathVariable("id") Long id){
        Optional<Car> car = carService.find(modelname, id);
        if (car.isPresent()){
            carService.deleteCar(car.get());
            return ResponseEntity.accepted().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Updates selected car (by ID)
     * @param modelname Model name
     * @param request   Car data parsed from JSON
     * @param id        ID of car
     * @returna ccepted if car were found, not found if weren't.
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> updateCar(@PathVariable("modelname") String modelname,
                                          @RequestBody UpdateCarRequest request,
                                          @PathVariable("id") Long id){
         Optional<Car> car = carService.find(modelname, id);
         if (car.isPresent()){
             UpdateCarRequest.dtoToEntityUpdater().apply(car.get(), request);
             carService.updateCar(car.get());
             return ResponseEntity.accepted().build();
         }
         else
             return ResponseEntity.notFound().build();
    }

}

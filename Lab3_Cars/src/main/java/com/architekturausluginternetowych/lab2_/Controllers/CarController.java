package com.architekturausluginternetowych.lab2_.Controllers;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.CarImage;
import com.architekturausluginternetowych.lab2_.Services.CarImageService;
import com.architekturausluginternetowych.lab2_.Services.CarService;
import com.architekturausluginternetowych.lab2_.Services.ModelService;
import com.architekturausluginternetowych.lab2_.dto.CreateCarRequest;
import com.architekturausluginternetowych.lab2_.dto.GetAllCarsResponse;
import com.architekturausluginternetowych.lab2_.dto.GetCarResponse;
import com.architekturausluginternetowych.lab2_.dto.UpdateCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("api/cars")
public class CarController {

    public CarService carService;

    public ModelService modelService;

    public CarImageService carImageService;

    @Autowired
    public CarController(CarService carService, ModelService modelService, CarImageService carImageService){
        this.carService = carService;
        this.modelService = modelService;
        this.carImageService = carImageService;
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

    @PutMapping(value = "/{vin}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadCarImage(@PathVariable("vin") String vin,
                                               @RequestParam("image") MultipartFile file,
                                               @RequestParam("author") String author,
                                               @RequestParam("description") String description) throws IOException {
        Optional<Car> car = carService.findCarByVIN(vin);
        if(car.isPresent()){
            CarImage carImage = CarImage.builder()
                    .vin(vin)
                    .author(author)
                    .description(description)
                    .build();

            carImageService.save(carImage, file);
            carService.updateImage(carImage);
            return ResponseEntity
                    .accepted()
                    .build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{vin}/download")
    public ResponseEntity<InputStreamResource> downloadCarImage(@PathVariable("vin") String vin) throws FileNotFoundException {
        Optional<File> carImage = carImageService.findImage(vin);
        if(carImage.isPresent()) {
            File file = carImage.get();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .headers(headers)
                    .body(resource);
        }
        else
            return ResponseEntity.notFound().build();
    }
}

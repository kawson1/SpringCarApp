package com.architekturausluginternetowych.lab2_.Services;

import com.architekturausluginternetowych.lab2_.Car;
import com.architekturausluginternetowych.lab2_.Model;
import com.architekturausluginternetowych.lab2_.Repositories.CarRepository;
import com.architekturausluginternetowych.lab2_.Repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    private ModelRepository modelRepository;

    @Autowired
    public CarService(CarRepository carRepository, ModelRepository modelRepository) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
    }

    public Car addCar(Car car){ return carRepository.save(car); }

    public void deleteCar(Car car){ carRepository.delete(car); }

    public Optional<Car> findCarById(Long id){
        return carRepository.findById(id);
    }

    public Optional<Car> findCarByVIN(String vin) { return carRepository.findByVin(vin); }

    /**
     * @param modelname name's model
     * @param id    car's id
     * @return selected car of specific model if present
     */
    public Optional<Car> find(String modelname, Long id){
        Optional<Model> model = modelRepository.findById(modelname);
        if (model.isPresent()){
            return carRepository.findByModelAndId(model.get(), id);
        }
        else
            return Optional.empty();
    }

    public Optional<Car> find(Model model, Long id){ return carRepository.findByModelAndId(model, id); }

    /**
     * @return all available cars
     */
    public List<Car> findAll(){ return carRepository.findAll(); }

    /**
     * @param model model of cars
     * @return list of cars specific model
     */
    public List<Car> findAll(Model model){ return carRepository.findAllByModel(model); }

    public void updateCar(Car car) {
        carRepository.save(car);
    }
}

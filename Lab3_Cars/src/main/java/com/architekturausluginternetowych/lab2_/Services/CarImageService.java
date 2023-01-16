package com.architekturausluginternetowych.lab2_.Services;

import com.architekturausluginternetowych.lab2_.CarImage;
import com.architekturausluginternetowych.lab2_.Repositories.CarImageRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class CarImageService {

    private final Path root = Paths.get("upload");
    private final String format = ".jpg";

    private CarImageRepository carImageRepository;

    @Autowired
    public CarImageService(CarImageRepository carImageRepository){
        this.carImageRepository = carImageRepository;
        try {
            Files.createDirectories(root);
        } catch (IOException ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(CarImage carImage, MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.root.resolve(carImage.getVin() + this.format), StandardCopyOption.REPLACE_EXISTING);
            carImageRepository.save(carImage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Optional<File> findImage(String vin){
        File file = new File(root.resolve(vin + this.format).toString());
        if (file.isFile()){
            return Optional.of(file);
        }
        else
            return Optional.empty();
    }
}

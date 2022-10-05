package com.example.CarRental.Controller;

import com.example.CarRental.Model.Car;
import com.example.CarRental.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping()
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/available")
    public List<Car> getAllAvailableCars(){
        return carService.getAllAvailableCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarById(@PathVariable Long id){
        try{
            Car car = carService.getCarById(id);
            return ResponseEntity.ok(car);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(e.getMessage());
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, @RequestBody Car carDetail){
        try{
            Car car = carService.getCarById(id);
            car.setBrand(carDetail.getBrand());
            car.setModel(carDetail.getModel());
            car.setYear(carDetail.getYear());
            car.setPrice(carDetail.getPrice());
            carService.saveCar(car);
            return ResponseEntity.ok(car);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        carService.saveCar(car);
        return ResponseEntity.ok(car);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id){
        try {
            carService.deleteCarById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return  ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }
}

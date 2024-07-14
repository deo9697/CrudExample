package demo.crud_example.controllers;


import demo.crud_example.entities.Car;
import demo.crud_example.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Car create(@RequestBody Car car) {
        Car carSaved = carRepository.saveAndFlush(car);
        return carSaved;
    }

    @GetMapping
    public List<Car> AllCars() {
        return carRepository.findAll();
    }

    // Restituisce una singola Car
    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) {
        return carRepository.findById(id)
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Car(), HttpStatus.OK));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateType(@PathVariable Integer id, @RequestParam String type) {
        return carRepository.findById(id)
                .map(car -> {
                    car.setType(type);
                    carRepository.saveAndFlush(car);
                    return new ResponseEntity<>(car, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(new Car(), HttpStatus.OK));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        carRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

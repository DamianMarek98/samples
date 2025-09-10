package car.repair.shop.springbootlambdaapi.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> foundCar = carService.findCarById(id);
        return foundCar
            .map(car -> ResponseEntity.ok(car))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<Car>> getAllCars() {
        Collection<Car> allCars = carService.findAllCars();
        return ResponseEntity.ok(allCars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car newCarRequest) {
        Car createdCar = carService.createCar(newCarRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        boolean wasCarDeleted = carService.deleteCarById(id);
        return wasCarDeleted
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}

package com.open.telemetry.opentelemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarRepo carRepository;
    private final RestClient restClient;

    public CarController(CarRepo carRepository, RestClient restClient) {
        this.carRepository = carRepository;
        this.restClient = restClient;
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        LOGGER.info("Adding car with VIN: {}", car.vin());
        carRepository.save(car);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Car> getCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(car -> {
            BigDecimal price = null;
            try {
                price = restClient.get()
                        .uri("http://localhost:8081/price?brand={brand}&model={model}", car.brand(), car.model())
                        .retrieve()
                        // handle 404 Not Found status
                        .onStatus(status -> status.value() == 404, (request, response) -> {
                            LOGGER.warn("Price service returned 404 for car {} {}", car.brand(), car.model());
                        })
                        .body(BigDecimal.class);
            } catch (Exception e) {
                LOGGER.warn("Could not fetch price for car {} {}: {}", car.brand(), car.model(), e.getMessage());
            }
            return car.withPrice(price);
        }).toList();
    }
}


// RestClient bean configuration
@Configuration
class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}

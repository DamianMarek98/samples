package com.open.telemetry.opentelemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarRepo carRepository;
    private final RestTemplate restTemplate;

    public CarController(CarRepo carRepository, RestTemplate restTemplate) {
        this.carRepository = carRepository;
        this.restTemplate = restTemplate;
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
                .map(this::getCarWithPrice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Car> getCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(this::getCarWithPrice).toList();
    }

    private Car getCarWithPrice(Car car) {
        BigDecimal price = null;
        try {
            price = restTemplate.getForObject(
                        "http://price-service:8081/price?brand={brand}&model={model}",
                        BigDecimal.class,
                        car.brand(),
                        car.model()
                    );
        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.warn("Price service returned 404 for car {} {}", car.brand(), car.model());
        } catch (Exception e) {
            LOGGER.warn("Could not fetch price for car {} {}: {}", car.brand(), car.model(), e.getMessage());
        }
        return car.withPrice(price);
    }
}


// RestClient bean configuration
@Configuration
class RestClientConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

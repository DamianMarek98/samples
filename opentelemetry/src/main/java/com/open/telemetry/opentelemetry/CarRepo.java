package com.open.telemetry.opentelemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarRepo.class);

    private final AtomicLong carId = new AtomicLong(0);
    private final Map<Long, Car> cars = new HashMap<>();

    public CarRepo() {
        Car car1 = new Car(carId.incrementAndGet(), CarBrand.TOYOTA, "Corolla", null, "123", "a", 2016);
        Car car2 = new Car(carId.incrementAndGet(), CarBrand.TOYOTA, "Camry", null, "1234", "adasbas", 2018);
        Car car3 = new Car(carId.incrementAndGet(), CarBrand.BMW, "X5", null, "123345", "dsadas", 2022);
        Car car4 = new Car(carId.incrementAndGet(), CarBrand.BMW, "320i", null, "123456", "1231", 2010);

        cars.put(car1.id(), car1);
        cars.put(car2.id(), car2);
        cars.put(car3.id(), car3);
        cars.put(car4.id(), car4);
    }

    public Car save(Car car) {
        cars.put(carId.incrementAndGet(), car);
        LOGGER.info("Saving car with id {}", carId.get());
        return car;
    }

    public Optional<Car> findById(Long id) {
        LOGGER.info("Finding car with id {}", id);
        return Optional.ofNullable(cars.get(id));
    }

    public List<Car> findAll() {
        LOGGER.info("Getting all cars");
        return new ArrayList<>(cars.values());
    }
}

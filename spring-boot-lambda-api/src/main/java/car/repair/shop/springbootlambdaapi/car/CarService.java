package car.repair.shop.springbootlambdaapi.car;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {
    private final Map<Long, Car> carRepository = new HashMap<>();
    private Long nextCarId = 3L;

    public CarService() {
        initializeWithExampleCars();
    }

    private void initializeWithExampleCars() {
        Car firstExampleCar = new Car(1L, "Toyota", "Red", "Camry", 2020, "1HGCM82633A123456");
        Car secondExampleCar = new Car(2L, "BMW", "Black", "X5", 2019, "WBAFN13506L123789");

        carRepository.put(firstExampleCar.getId(), firstExampleCar);
        carRepository.put(secondExampleCar.getId(), secondExampleCar);
    }

    public Optional<Car> findCarById(Long carId) {
        return Optional.ofNullable(carRepository.get(carId));
    }

    public Collection<Car> findAllCars() {
        return new ArrayList<>(carRepository.values());
    }

    public Car createCar(Car newCarData) {
        Car carToSave = new Car(
            nextCarId++,
            newCarData.getBrandName(),
            newCarData.getColor(),
            newCarData.getModel(),
            newCarData.getYear(),
            newCarData.getVin()
        );

        carRepository.put(carToSave.getId(), carToSave);
        return carToSave;
    }

    public boolean deleteCarById(Long carId) {
        return carRepository.remove(carId) != null;
    }
}

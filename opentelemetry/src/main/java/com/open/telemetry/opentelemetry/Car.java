package com.open.telemetry.opentelemetry;

import java.math.BigDecimal;

public record Car(Long id,
                  CarBrand brand,
                  String model,
                  BigDecimal price,
                  String vin,
                  String plateNumber,
                  int productionYear) {

    public Car withPrice(BigDecimal price) {
        return new Car(id, brand, model, price, vin, plateNumber, productionYear);
    }
}

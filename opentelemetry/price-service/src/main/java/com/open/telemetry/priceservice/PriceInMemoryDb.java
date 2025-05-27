package com.open.telemetry.priceservice;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PriceInMemoryDb {
    private final Map<CarBrand, Map<String, BigDecimal>> priceData = new HashMap<>();

    public PriceInMemoryDb() {
        Map<String, BigDecimal> toyotaModels = new HashMap<>();
        toyotaModels.put("Corolla", new BigDecimal("20000"));
        toyotaModels.put("Camry", new BigDecimal("25000"));

        Map<String, BigDecimal> bmwModels = new HashMap<>();
        bmwModels.put("X5", new BigDecimal("50000"));
        bmwModels.put("320i", new BigDecimal("35000"));

        priceData.put(CarBrand.TOYOTA, toyotaModels);
        priceData.put(CarBrand.BMW, bmwModels);
    }

    public Map<String, BigDecimal> getModelsByBrand(CarBrand brand) {
        return priceData.get(brand);
    }
}

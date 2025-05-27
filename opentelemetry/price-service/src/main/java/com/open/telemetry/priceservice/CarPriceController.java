package com.open.telemetry.priceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/price")
public class CarPriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarPriceController.class);

    private final PriceInMemoryDb priceInMemoryDb;

    public CarPriceController(PriceInMemoryDb priceInMemoryDb) {
        this.priceInMemoryDb = priceInMemoryDb;
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getPrice(
            @RequestParam CarBrand brand,
            @RequestParam String model) {
        LOGGER.info("Get price for brand {} and model {}", brand, model);
        Map<String, BigDecimal> models = priceInMemoryDb.getModelsByBrand(brand);
        if (models != null) {
            BigDecimal price = models.get(model);
            if (price != null) {
                LOGGER.info("Price found: {}", price);
                return ResponseEntity.ok(price);
            }
        }

        LOGGER.info("Price not found for brand {} and model {}", brand, model);
        return ResponseEntity.notFound().build();
    }
}
